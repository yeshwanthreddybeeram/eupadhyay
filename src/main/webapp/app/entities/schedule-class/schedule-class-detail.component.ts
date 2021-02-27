import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScheduleClass } from 'app/shared/model/schedule-class.model';

@Component({
  selector: 'jhi-schedule-class-detail',
  templateUrl: './schedule-class-detail.component.html',
})
export class ScheduleClassDetailComponent implements OnInit {
  scheduleClass: IScheduleClass | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleClass }) => (this.scheduleClass = scheduleClass));
  }

  previousState(): void {
    window.history.back();
  }
}
