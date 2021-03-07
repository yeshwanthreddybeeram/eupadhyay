import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssignmentQA } from 'app/shared/model/assignment-qa.model';

@Component({
  selector: 'jhi-assignment-qa-detail',
  templateUrl: './assignment-qa-detail.component.html',
})
export class AssignmentQADetailComponent implements OnInit {
  assignmentQA: IAssignmentQA | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ assignmentQA }) => (this.assignmentQA = assignmentQA));
  }

  previousState(): void {
    window.history.back();
  }
}
