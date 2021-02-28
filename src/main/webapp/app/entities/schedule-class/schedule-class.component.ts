import { Component, OnInit, OnDestroy } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Authority } from 'app/shared/constants/authority.constants';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';

import { combineLatest, Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IScheduleClass } from 'app/shared/model/schedule-class.model';
import { ScheduleClassService } from './schedule-class.service';
import { ScheduleClassDeleteDialogComponent } from './schedule-class-delete-dialog.component';

@Component({
  selector: 'jhi-schedule-class',
  templateUrl: './schedule-class.component.html',
})
export class ScheduleClassComponent implements OnInit, OnDestroy {
  scheduleClasses?: IScheduleClass[];
  eventSubscriber?: Subscription;
  isSaving = false;
  currentAccount: Account | null = null;
  page!: number;
  predicate!: string;
  ascending!: boolean;

  constructor(
    protected scheduleClassService: ScheduleClassService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    protected accountService: AccountService
  ) {}

  previousState(): void {
    window.history.back();
  }

  loadAll(): void {
    this.scheduleClassService.query().subscribe((res: HttpResponse<IScheduleClass[]>) => (this.scheduleClasses = res.body || []));
  }

  loadStudentSchedules(): void {
    this.scheduleClassService
      .findsStudentClasses()
      .subscribe((res: HttpResponse<IScheduleClass[]>) => (this.scheduleClasses = res.body || []));
  }

  loadEmployeeSchedule(): void {
    this.scheduleClassService
      .findsEmployeeClasses()
      .subscribe((res: HttpResponse<IScheduleClass[]>) => (this.scheduleClasses = res.body || []));
  }

  loadUserSpecficSchedules(): void {
    if (this.accountService.hasAnyAuthority(Authority.ADMIN)) {
      this.loadAll();
    } else if (this.accountService.hasAnyAuthority(Authority.EMPLOYEE)) {
      this.loadEmployeeSchedule();
    } else if (this.accountService.hasAnyAuthority([Authority.STUDENT, Authority.USER])) {
      this.loadStudentSchedules();
    }
  }

  ngOnInit(): void {
    if (this.accountService.hasAnyAuthority(Authority.ADMIN)) {
      this.loadAll();
    } else if (this.accountService.hasAnyAuthority(Authority.EMPLOYEE)) {
      this.loadEmployeeSchedule();
    } else if (this.accountService.hasAnyAuthority([Authority.STUDENT, Authority.USER])) {
      this.loadStudentSchedules();
    }

    this.registerChangeInScheduleClasses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IScheduleClass): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInScheduleClasses(): void {
    this.eventSubscriber = this.eventManager.subscribe('scheduleClassListModification', () => this.loadAll());
  }

  delete(scheduleClass: IScheduleClass): void {
    const modalRef = this.modalService.open(ScheduleClassDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.scheduleClass = scheduleClass;
  }

  markComplete(scheduleClass: IScheduleClass, isComplete: boolean): void {
    scheduleClass.complete = isComplete;
    this.save(scheduleClass);
  }

  save(scheduleClass: IScheduleClass): void {
    this.isSaving = true;
    if (scheduleClass.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleClassService.update(scheduleClass));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScheduleClass>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
      },
    });
  }

  private sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      this.page = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === 'asc';
      this.loadUserSpecficSchedules();
    }).subscribe();
  }
}
