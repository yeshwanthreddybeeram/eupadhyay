import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGuest, Guest } from 'app/shared/model/guest.model';
import { GuestService } from './guest.service';

@Component({
  selector: 'jhi-guest-update',
  templateUrl: './guest-update.component.html',
})
export class GuestUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    email: [],
    mobilenumber: [],
    subject: [],
    message: [],
    reply: [],
  });

  constructor(
    protected guestService: GuestService,
    protected activatedRoute: ActivatedRoute,
    public activeModal: NgbActiveModal,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ guest }) => {
      this.updateForm(guest);
    });
  }

  updateForm(guest: IGuest): void {
    this.editForm.patchValue({
      id: guest.id,
      name: guest.name,
      email: guest.email,
      mobilenumber: guest.mobilenumber,
      subject: guest.subject,
      message: guest.message,
      reply: guest.reply,
    });
  }

  previousState(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    this.isSaving = true;
    const guest = this.createFromForm();
    if (guest.id === undefined) {
      this.subscribeToSaveResponse(this.guestService.create(guest));
    } else if (guest.id === null) {
      this.subscribeToSaveResponse(this.guestService.update(guest));
    } else {
      this.subscribeToSaveResponse(this.guestService.update(guest));
    }
  }

  private createFromForm(): IGuest {
    return {
      ...new Guest(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      mobilenumber: this.editForm.get(['mobilenumber'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      message: this.editForm.get(['message'])!.value,
      reply: this.editForm.get(['reply'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGuest>>): void {
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
