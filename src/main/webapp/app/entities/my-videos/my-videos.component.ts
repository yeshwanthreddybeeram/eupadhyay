import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMyVideos } from 'app/shared/model/my-videos.model';
import { MyVideosService } from './my-videos.service';
import { MyVideosDeleteDialogComponent } from './my-videos-delete-dialog.component';

@Component({
  selector: 'jhi-my-videos',
  templateUrl: './my-videos.component.html',
})
export class MyVideosComponent implements OnInit, OnDestroy {
  myVideos?: IMyVideos[];
  eventSubscriber?: Subscription;

  constructor(protected myVideosService: MyVideosService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.myVideosService.query().subscribe((res: HttpResponse<IMyVideos[]>) => (this.myVideos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMyVideos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMyVideos): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMyVideos(): void {
    this.eventSubscriber = this.eventManager.subscribe('myVideosListModification', () => this.loadAll());
  }

  delete(myVideos: IMyVideos): void {
    const modalRef = this.modalService.open(MyVideosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.myVideos = myVideos;
  }
}
