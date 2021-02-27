import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMyVideos, MyVideos } from 'app/shared/model/my-videos.model';
import { MyVideosService } from './my-videos.service';

@Component({
  selector: 'jhi-my-videos-update',
  templateUrl: './my-videos-update.component.html',
})
export class MyVideosUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    videoLink: [],
    conceptname: [],
    scheduleTime: [],
  });

  constructor(protected myVideosService: MyVideosService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myVideos }) => {
      if (!myVideos.id) {
        const today = moment().startOf('day');
        myVideos.scheduleTime = today;
      }

      this.updateForm(myVideos);
    });
  }

  updateForm(myVideos: IMyVideos): void {
    this.editForm.patchValue({
      id: myVideos.id,
      videoLink: myVideos.videoLink,
      conceptname: myVideos.conceptname,
      scheduleTime: myVideos.scheduleTime ? myVideos.scheduleTime.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const myVideos = this.createFromForm();
    if (myVideos.id !== undefined) {
      this.subscribeToSaveResponse(this.myVideosService.update(myVideos));
    } else {
      this.subscribeToSaveResponse(this.myVideosService.create(myVideos));
    }
  }

  private createFromForm(): IMyVideos {
    return {
      ...new MyVideos(),
      id: this.editForm.get(['id'])!.value,
      videoLink: this.editForm.get(['videoLink'])!.value,
      conceptname: this.editForm.get(['conceptname'])!.value,
      scheduleTime: this.editForm.get(['scheduleTime'])!.value
        ? moment(this.editForm.get(['scheduleTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMyVideos>>): void {
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
