import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAssignment, Assignment } from 'app/shared/model/assignment.model';
import { AssignmentService } from './assignment.service';

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
  });

  constructor(protected assignmentService: AssignmentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assignment }) => {
      this.updateForm(assignment);
    });
  }

  updateForm(assignment: IAssignment): void {
    this.editForm.patchValue({
      id: assignment.id,
      subject: assignment.subject,
      description: assignment.description,
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
