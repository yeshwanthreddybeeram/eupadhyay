import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVideoLink } from 'app/shared/model/video-link.model';
import { VideoLinkService } from './video-link.service';

@Component({
  templateUrl: './video-link-delete-dialog.component.html',
})
export class VideoLinkDeleteDialogComponent {
  videoLink?: IVideoLink;

  constructor(protected videoLinkService: VideoLinkService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.videoLinkService.delete(id).subscribe(() => {
      this.eventManager.broadcast('videoLinkListModification');
      this.activeModal.close();
    });
  }
}
