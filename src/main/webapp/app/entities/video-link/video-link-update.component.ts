import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVideoLink, VideoLink } from 'app/shared/model/video-link.model';
import { VideoLinkService } from './video-link.service';

@Component({
  selector: 'jhi-video-link-update',
  templateUrl: './video-link-update.component.html',
})
export class VideoLinkUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    classlink: [null, [Validators.required]],
  });

  constructor(protected videoLinkService: VideoLinkService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ videoLink }) => {
      this.updateForm(videoLink);
    });
  }

  updateForm(videoLink: IVideoLink): void {
    this.editForm.patchValue({
      id: videoLink.id,
      classlink: videoLink.classlink,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const videoLink = this.createFromForm();
    if (videoLink.id !== undefined) {
      this.subscribeToSaveResponse(this.videoLinkService.update(videoLink));
    } else {
      this.subscribeToSaveResponse(this.videoLinkService.create(videoLink));
    }
  }

  private createFromForm(): IVideoLink {
    return {
      ...new VideoLink(),
      id: this.editForm.get(['id'])!.value,
      classlink: this.editForm.get(['classlink'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideoLink>>): void {
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
