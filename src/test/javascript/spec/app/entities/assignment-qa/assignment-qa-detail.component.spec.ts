import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { AssignmentQADetailComponent } from 'app/entities/assignment-qa/assignment-qa-detail.component';
import { AssignmentQA } from 'app/shared/model/assignment-qa.model';

describe('Component Tests', () => {
  describe('AssignmentQA Management Detail Component', () => {
    let comp: AssignmentQADetailComponent;
    let fixture: ComponentFixture<AssignmentQADetailComponent>;
    const route = ({ data: of({ assignmentQA: new AssignmentQA(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [AssignmentQADetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AssignmentQADetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssignmentQADetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load assignmentQA on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assignmentQA).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
