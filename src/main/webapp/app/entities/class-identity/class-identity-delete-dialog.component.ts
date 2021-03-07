import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClassIdentity } from 'app/shared/model/class-identity.model';
import { ClassIdentityService } from './class-identity.service';

@Component({
  templateUrl: './class-identity-delete-dialog.component.html',
})
export class ClassIdentityDeleteDialogComponent {
  classIdentity?: IClassIdentity;

  constructor(
    protected classIdentityService: ClassIdentityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.classIdentityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('classIdentityListModification');
      this.activeModal.close();
    });
  }
}
