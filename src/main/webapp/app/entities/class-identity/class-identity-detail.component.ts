import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClassIdentity } from 'app/shared/model/class-identity.model';

@Component({
  selector: 'jhi-class-identity-detail',
  templateUrl: './class-identity-detail.component.html',
})
export class ClassIdentityDetailComponent implements OnInit {
  classIdentity: IClassIdentity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ classIdentity }) => (this.classIdentity = classIdentity));
  }

  previousState(): void {
    window.history.back();
  }
}
