import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IAssignment, Assignment } from 'app/shared/model/assignment.model';
import { AssignmentService } from './assignment.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-assignment-update',
  templateUrl: './assignment-update.component.html',
})
export class AssignmentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    subject: [],
    description: [],
    studentloginname: [],
    employeeloginname: [],
    status: [],
    submitdate: [],
    marks: [],
    remarks: [],
    asgnstatus: [],
    assignmentlink: [],
    submitlink: [],
    assignmentpdf: [],
    assignmentpdfContentType: [],
    submitpdf: [],
    submitpdfContentType: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected assignmentService: AssignmentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assignment }) => {
      if (!assignment.id) {
        const today = moment().startOf('day');
        assignment.submitdate = today;
      }

      this.updateForm(assignment);
    });
  }

  updateForm(assignment: IAssignment): void {
    this.editForm.patchValue({
      id: assignment.id,
      subject: assignment.subject,
      description: assignment.description,
      studentloginname: assignment.studentloginname,
      employeeloginname: assignment.employeeloginname,
      status: assignment.status,
      submitdate: assignment.submitdate ? assignment.submitdate.format(DATE_TIME_FORMAT) : null,
      marks: assignment.marks,
      remarks: assignment.remarks,
      asgnstatus: assignment.asgnstatus,
      assignmentlink: assignment.assignmentlink,
      submitlink: assignment.submitlink,
      assignmentpdf: assignment.assignmentpdf,
      assignmentpdfContentType: assignment.assignmentpdfContentType,
      submitpdf: assignment.submitpdf,
      submitpdfContentType: assignment.submitpdfContentType,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('devpctApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assignment = this.createFromForm();
    if (assignment.id !== undefined) {
      this.subscribeToSaveResponse(this.assignmentService.update(assignment));
    } else {
      this.subscribeToSaveResponse(this.assignmentService.create(assignment));
    }
  }

  private createFromForm(): IAssignment {
    return {
      ...new Assignment(),
      id: this.editForm.get(['id'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      description: this.editForm.get(['description'])!.value,
      studentloginname: this.editForm.get(['studentloginname'])!.value,
      employeeloginname: this.editForm.get(['employeeloginname'])!.value,
      status: this.editForm.get(['status'])!.value,
      submitdate: this.editForm.get(['submitdate'])!.value ? moment(this.editForm.get(['submitdate'])!.value, DATE_TIME_FORMAT) : undefined,
      marks: this.editForm.get(['marks'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      asgnstatus: this.editForm.get(['asgnstatus'])!.value,
      assignmentlink: this.editForm.get(['assignmentlink'])!.value,
      submitlink: this.editForm.get(['submitlink'])!.value,
      assignmentpdfContentType: this.editForm.get(['assignmentpdfContentType'])!.value,
      assignmentpdf: this.editForm.get(['assignmentpdf'])!.value,
      submitpdfContentType: this.editForm.get(['submitpdfContentType'])!.value,
      submitpdf: this.editForm.get(['submitpdf'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssignment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
