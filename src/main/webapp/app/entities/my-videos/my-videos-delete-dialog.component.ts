import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMyVideos } from 'app/shared/model/my-videos.model';
import { MyVideosService } from './my-videos.service';

@Component({
  templateUrl: './my-videos-delete-dialog.component.html',
})
export class MyVideosDeleteDialogComponent {
  myVideos?: IMyVideos;

  constructor(protected myVideosService: MyVideosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.myVideosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('myVideosListModification');
      this.activeModal.close();
    });
  }
}
