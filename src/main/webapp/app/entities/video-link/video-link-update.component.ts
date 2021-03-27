import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVideoLink, VideoLink } from 'app/shared/model/video-link.model';
import { VideoLinkService } from './video-link.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { IStudyMaterials } from 'app/shared/model/study-materials.model';
import { StudyMaterialsService } from '../study-materials/study-materials.service';

@Component({
  selector: 'jhi-video-link-update',
  templateUrl: './video-link-update.component.html',
})
export class VideoLinkUpdateComponent implements OnInit {
  isSaving = false;
  videoLink?: IVideoLink | null = null;
  studyMaterials?: IStudyMaterials | null = null;

  editForm = this.fb.group({
    id: [],
    classlink: [null, [Validators.required]],
    linkName: [],
    linkDescription: [],
    submitLink: [],
  });

  constructor(
    protected videoLinkService: VideoLinkService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager,
    private studyMaterialsService: StudyMaterialsService
  ) {}

  ngOnInit(): void {
    if (this.videoLink) {
      this.updateForm(this.videoLink);
    }
  }

  updateForm(videoLink: IVideoLink): void {
    this.editForm.patchValue({
      id: videoLink.id,
      classlink: videoLink.classlink,
      linkName: videoLink.linkName,
      linkDescription: videoLink.linkDescription,
      submitLink: videoLink.submitLink,
    });
  }

  previousState(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    this.isSaving = true;
    const videoLink = this.createFromForm();
    if (videoLink.id !== undefined) {
      this.subscribeToSaveResponse(this.videoLinkService.update(videoLink));
    } else {
      this.subscribeToSaveResponse(this.videoLinkService.create(videoLink));
    }
    this.eventManager.broadcast('videoLinkListModification');
  }

  createToStudyMaterial(): void {
    this.isSaving = true;
    const videoLink = this.createFromForm();
    this.videoLinkService.create(videoLink).subscribe((res: HttpResponse<IVideoLink>) => {
      this.videoLink = res.body;
      if (res.body && this.studyMaterials) {
        this.studyMaterials?.videoLinks?.push(res.body);
        this.subscribeToSMSaveResponse(this.studyMaterialsService.update(this.studyMaterials));
        this.eventManager.broadcast('videoLinkListModification');
      }
    });
    this.eventManager.broadcast('videoLinkListModification');
  }

  private createFromForm(): IVideoLink {
    return {
      ...new VideoLink(),
      id: this.editForm.get(['id'])!.value,
      classlink: this.editForm.get(['classlink'])!.value,
      linkName: this.editForm.get(['linkName'])!.value,
      linkDescription: this.editForm.get(['linkDescription'])!.value,
      submitLink: this.editForm.get(['submitLink'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideoLink>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected subscribeToSMSaveResponse(result: Observable<HttpResponse<IStudyMaterials>>): void {
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
