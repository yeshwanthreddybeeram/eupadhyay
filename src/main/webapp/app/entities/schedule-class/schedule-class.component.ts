import { Component, OnInit, OnDestroy } from '@angular/core';
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

  constructor(
    protected scheduleClassService: ScheduleClassService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.scheduleClassService.query().subscribe((res: HttpResponse<IScheduleClass[]>) => (this.scheduleClasses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
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
}
