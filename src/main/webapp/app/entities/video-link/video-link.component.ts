import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVideoLink } from 'app/shared/model/video-link.model';
import { VideoLinkService } from './video-link.service';
import { VideoLinkDeleteDialogComponent } from './video-link-delete-dialog.component';

@Component({
  selector: 'jhi-video-link',
  templateUrl: './video-link.component.html',
})
export class VideoLinkComponent implements OnInit, OnDestroy {
  videoLinks?: IVideoLink[];
  eventSubscriber?: Subscription;

  constructor(protected videoLinkService: VideoLinkService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.videoLinkService.query().subscribe((res: HttpResponse<IVideoLink[]>) => (this.videoLinks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVideoLinks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVideoLink): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVideoLinks(): void {
    this.eventSubscriber = this.eventManager.subscribe('videoLinkListModification', () => this.loadAll());
  }

  delete(videoLink: IVideoLink): void {
    const modalRef = this.modalService.open(VideoLinkDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.videoLink = videoLink;
  }
}
