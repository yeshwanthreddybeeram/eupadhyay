import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStudyMaterials } from 'app/shared/model/study-materials.model';
import { IVideoLink } from 'app/shared/model/video-link.model';
import { JhiEventManager } from 'ng-jhipster';
import { Subscription } from 'rxjs';
import { VideoLinkDeleteDialogComponent } from '../video-link/video-link-delete-dialog.component';
import { VideoLinkUpdateComponent } from '../video-link/video-link-update.component';
import { StudyMaterialsService } from './study-materials.service';

@Component({
  selector: 'jhi-study-materials-detail',
  templateUrl: './study-materials-detail.component.html',
})
export class StudyMaterialsDetailComponent implements OnInit {
  studyMaterials?: IStudyMaterials | null = null;
  eventSubscriber?: Subscription;
  videolinks: IVideoLink[] = [];

  constructor(
    protected activatedRoute: ActivatedRoute,
    private modalService: NgbModal,
    protected eventManager: JhiEventManager,
    protected studyMaterialsService: StudyMaterialsService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studyMaterials }) => (this.studyMaterials = studyMaterials));
    this.registerChangeInVideoLinks();
  }

  previousState(): void {
    window.history.back();
  }

  trackId(index: number, item: IVideoLink): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  delete(videoLink: IVideoLink): void {
    const modalRef = this.modalService.open(VideoLinkDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.videoLink = videoLink;
  }

  update(videoLink: IVideoLink): void {
    const modalRef = this.modalService.open(VideoLinkUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.videoLink = videoLink;
  }

  create(): void {
    const modalRef = this.modalService.open(VideoLinkUpdateComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.studyMaterials = this.studyMaterials;
  }

  registerChangeInVideoLinks(): void {
    if (this.studyMaterials?.id) {
      const studyMaterialID = this.studyMaterials?.id;
      this.eventSubscriber = this.eventManager.subscribe('videoLinkListModification', () => {
        this.studyMaterialsService
          .find(studyMaterialID)
          .subscribe((res: HttpResponse<IStudyMaterials>) => (this.studyMaterials = res.body));
      });
    }
  }
}
