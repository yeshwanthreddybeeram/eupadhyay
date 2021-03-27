import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStudyMaterials } from 'app/shared/model/study-materials.model';
import { StudyMaterialsService } from './study-materials.service';

@Component({
  templateUrl: './study-materials-delete-dialog.component.html',
})
export class StudyMaterialsDeleteDialogComponent {
  studyMaterials?: IStudyMaterials;

  constructor(
    protected studyMaterialsService: StudyMaterialsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.studyMaterialsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('studyMaterialsListModification');
      this.activeModal.close();
    });
  }
}
