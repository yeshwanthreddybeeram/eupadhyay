import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssignmentQA } from 'app/shared/model/assignment-qa.model';
import { AssignmentQAService } from './assignment-qa.service';

@Component({
  templateUrl: './assignment-qa-delete-dialog.component.html',
})
export class AssignmentQADeleteDialogComponent {
  assignmentQA?: IAssignmentQA;

  constructor(
    protected assignmentQAService: AssignmentQAService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.assignmentQAService.delete(id).subscribe(() => {
      this.eventManager.broadcast('assignmentQAListModification');
      this.activeModal.close();
    });
  }
}
