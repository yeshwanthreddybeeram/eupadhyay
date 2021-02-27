import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVideoLink } from 'app/shared/model/video-link.model';

@Component({
  selector: 'jhi-video-link-detail',
  templateUrl: './video-link-detail.component.html',
})
export class VideoLinkDetailComponent implements OnInit {
  videoLink: IVideoLink | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ videoLink }) => (this.videoLink = videoLink));
  }

  previousState(): void {
    window.history.back();
  }
}
