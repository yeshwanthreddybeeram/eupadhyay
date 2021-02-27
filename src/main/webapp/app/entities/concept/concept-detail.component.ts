import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConcept } from 'app/shared/model/concept.model';

@Component({
  selector: 'jhi-concept-detail',
  templateUrl: './concept-detail.component.html',
})
export class ConceptDetailComponent implements OnInit {
  concept: IConcept | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ concept }) => (this.concept = concept));
  }

  previousState(): void {
    window.history.back();
  }
}
