import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStudyMaterials, StudyMaterials } from 'app/shared/model/study-materials.model';
import { StudyMaterialsService } from './study-materials.service';
import { IVideoLink } from 'app/shared/model/video-link.model';
import { VideoLinkService } from 'app/entities/video-link/video-link.service';

@Component({
  selector: 'jhi-study-materials-update',
  templateUrl: './study-materials-update.component.html',
})
export class StudyMaterialsUpdateComponent implements OnInit {
  isSaving = false;
  videolinks: IVideoLink[] = [];

  editForm = this.fb.group({
    id: [],
    folderName: [],
    folderDescription: [],
    foldertype: [],
    videoLinks: [],
  });

  constructor(
    protected studyMaterialsService: StudyMaterialsService,
    protected videoLinkService: VideoLinkService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studyMaterials }) => {
      this.updateForm(studyMaterials);

      this.videoLinkService.query().subscribe((res: HttpResponse<IVideoLink[]>) => (this.videolinks = res.body || []));
    });
  }

  updateForm(studyMaterials: IStudyMaterials): void {
    this.editForm.patchValue({
      id: studyMaterials.id,
      folderName: studyMaterials.folderName,
      folderDescription: studyMaterials.folderDescription,
      foldertype: studyMaterials.foldertype,
      videoLinks: studyMaterials.videoLinks,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const studyMaterials = this.createFromForm();
    if (studyMaterials.id !== undefined) {
      this.subscribeToSaveResponse(this.studyMaterialsService.update(studyMaterials));
    } else {
      this.subscribeToSaveResponse(this.studyMaterialsService.create(studyMaterials));
    }
  }

  private createFromForm(): IStudyMaterials {
    return {
      ...new StudyMaterials(),
      id: this.editForm.get(['id'])!.value,
      folderName: this.editForm.get(['folderName'])!.value,
      folderDescription: this.editForm.get(['folderDescription'])!.value,
      foldertype: this.editForm.get(['foldertype'])!.value,
      videoLinks: this.editForm.get(['videoLinks'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudyMaterials>>): void {
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

  trackById(index: number, item: IVideoLink): any {
    return item.id;
  }

  getSelected(selectedVals: IVideoLink[], option: IVideoLink): IVideoLink {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
