import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IClassIdentity, ClassIdentity } from 'app/shared/model/class-identity.model';
import { ClassIdentityService } from './class-identity.service';

@Component({
  selector: 'jhi-class-identity-update',
  templateUrl: './class-identity-update.component.html',
})
export class ClassIdentityUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    className: [],
    description: [],
  });

  constructor(protected classIdentityService: ClassIdentityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classIdentity }) => {
      this.updateForm(classIdentity);
    });
  }

  updateForm(classIdentity: IClassIdentity): void {
    this.editForm.patchValue({
      id: classIdentity.id,
      className: classIdentity.className,
      description: classIdentity.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const classIdentity = this.createFromForm();
    if (classIdentity.id !== undefined) {
      this.subscribeToSaveResponse(this.classIdentityService.update(classIdentity));
    } else {
      this.subscribeToSaveResponse(this.classIdentityService.create(classIdentity));
    }
  }

  private createFromForm(): IClassIdentity {
    return {
      ...new ClassIdentity(),
      id: this.editForm.get(['id'])!.value,
      className: this.editForm.get(['className'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClassIdentity>>): void {
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
