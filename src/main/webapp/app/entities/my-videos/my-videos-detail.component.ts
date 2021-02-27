import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMyVideos } from 'app/shared/model/my-videos.model';

@Component({
  selector: 'jhi-my-videos-detail',
  templateUrl: './my-videos-detail.component.html',
})
export class MyVideosDetailComponent implements OnInit {
  myVideos: IMyVideos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myVideos }) => (this.myVideos = myVideos));
  }

  previousState(): void {
    window.history.back();
  }
}
