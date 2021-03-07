import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAssignmentQA, AssignmentQA } from 'app/shared/model/assignment-qa.model';
import { AssignmentQAService } from './assignment-qa.service';

@Component({
  selector: 'jhi-assignment-qa-update',
  templateUrl: './assignment-qa-update.component.html',
})
export class AssignmentQAUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    question: [],
    answer: [],
  });

  constructor(protected assignmentQAService: AssignmentQAService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assignmentQA }) => {
      this.updateForm(assignmentQA);
    });
  }

  updateForm(assignmentQA: IAssignmentQA): void {
    this.editForm.patchValue({
      id: assignmentQA.id,
      question: assignmentQA.question,
      answer: assignmentQA.answer,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const assignmentQA = this.createFromForm();
    if (assignmentQA.id !== undefined) {
      this.subscribeToSaveResponse(this.assignmentQAService.update(assignmentQA));
    } else {
      this.subscribeToSaveResponse(this.assignmentQAService.create(assignmentQA));
    }
  }

  private createFromForm(): IAssignmentQA {
    return {
      ...new AssignmentQA(),
      id: this.editForm.get(['id'])!.value,
      question: this.editForm.get(['question'])!.value,
      answer: this.editForm.get(['answer'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAssignmentQA>>): void {
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
