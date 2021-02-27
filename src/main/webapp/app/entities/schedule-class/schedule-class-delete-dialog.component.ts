import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScheduleClass } from 'app/shared/model/schedule-class.model';
import { ScheduleClassService } from './schedule-class.service';

@Component({
  templateUrl: './schedule-class-delete-dialog.component.html',
})
export class ScheduleClassDeleteDialogComponent {
  scheduleClass?: IScheduleClass;

  constructor(
    protected scheduleClassService: ScheduleClassService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scheduleClassService.delete(id).subscribe(() => {
      this.eventManager.broadcast('scheduleClassListModification');
      this.activeModal.close();
    });
  }
}
