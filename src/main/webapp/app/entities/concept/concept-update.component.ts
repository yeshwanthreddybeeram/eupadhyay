import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IConcept, Concept } from 'app/shared/model/concept.model';
import { ConceptService } from './concept.service';
import { IVideoLink } from 'app/shared/model/video-link.model';
import { VideoLinkService } from 'app/entities/video-link/video-link.service';

@Component({
  selector: 'jhi-concept-update',
  templateUrl: './concept-update.component.html',
})
export class ConceptUpdateComponent implements OnInit {
  isSaving = false;
  videolinks: IVideoLink[] = [];

  editForm = this.fb.group({
    id: [],
    conceptname: [],
    videoLinks: [],
  });

  constructor(
    protected conceptService: ConceptService,
    protected videoLinkService: VideoLinkService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concept }) => {
      this.updateForm(concept);

      this.videoLinkService.query().subscribe((res: HttpResponse<IVideoLink[]>) => (this.videolinks = res.body || []));
    });
  }

  updateForm(concept: IConcept): void {
    this.editForm.patchValue({
      id: concept.id,
      conceptname: concept.conceptname,
      videoLinks: concept.videoLinks,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const concept = this.createFromForm();
    if (concept.id !== undefined) {
      this.subscribeToSaveResponse(this.conceptService.update(concept));
    } else {
      this.subscribeToSaveResponse(this.conceptService.create(concept));
    }
  }

  private createFromForm(): IConcept {
    return {
      ...new Concept(),
      id: this.editForm.get(['id'])!.value,
      conceptname: this.editForm.get(['conceptname'])!.value,
      videoLinks: this.editForm.get(['videoLinks'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConcept>>): void {
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
