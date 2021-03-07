import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAssignment } from 'app/shared/model/assignment.model';
import { AssignmentService } from './assignment.service';
import { AssignmentDeleteDialogComponent } from './assignment-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { Authority } from 'app/shared/constants/authority.constants';

@Component({
  selector: 'jhi-assignment',
  templateUrl: './assignment.component.html',
})
export class AssignmentComponent implements OnInit, OnDestroy {
  assignments?: IAssignment[];
  eventSubscriber?: Subscription;

  constructor(
    protected assignmentService: AssignmentService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    private accountService: AccountService,

    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.assignmentService.query().subscribe((res: HttpResponse<IAssignment[]>) => (this.assignments = res.body || []));
  }

  loadStudentAssignments(): void {
    this.assignmentService.loadStudentAssignments().subscribe((res: HttpResponse<IAssignment[]>) => (this.assignments = res.body || []));
  }

  loadEmployeeGivenAssignments(): void {
    this.assignmentService
      .loadEmployeeGivenAssignments()
      .subscribe((res: HttpResponse<IAssignment[]>) => (this.assignments = res.body || []));
  }

  ngOnInit(): void {
    if (this.accountService.hasAnyAuthority(Authority.ADMIN)) {
      this.loadAll();
    } else if (this.accountService.hasAnyAuthority(Authority.EMPLOYEE)) {
      this.loadEmployeeGivenAssignments();
    } else if (this.accountService.hasAnyAuthority([Authority.STUDENT, Authority.USER])) {
      this.loadStudentAssignments();
    }
    this.registerChangeInAssignments();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAssignment): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInAssignments(): void {
    this.eventSubscriber = this.eventManager.subscribe('assignmentListModification', () => this.loadAll());
  }

  delete(assignment: IAssignment): void {
    const modalRef = this.modalService.open(AssignmentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.assignment = assignment;
  }
}
