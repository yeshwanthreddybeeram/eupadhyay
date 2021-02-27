import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConcept } from 'app/shared/model/concept.model';
import { ConceptService } from './concept.service';
import { ConceptDeleteDialogComponent } from './concept-delete-dialog.component';

@Component({
  selector: 'jhi-concept',
  templateUrl: './concept.component.html',
})
export class ConceptComponent implements OnInit, OnDestroy {
  concepts?: IConcept[];
  eventSubscriber?: Subscription;

  constructor(protected conceptService: ConceptService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.conceptService.query().subscribe((res: HttpResponse<IConcept[]>) => (this.concepts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConcepts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConcept): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConcepts(): void {
    this.eventSubscriber = this.eventManager.subscribe('conceptListModification', () => this.loadAll());
  }

  delete(concept: IConcept): void {
    const modalRef = this.modalService.open(ConceptDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.concept = concept;
  }
}
