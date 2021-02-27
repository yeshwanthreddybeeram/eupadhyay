import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { ConceptDetailComponent } from 'app/entities/concept/concept-detail.component';
import { Concept } from 'app/shared/model/concept.model';

describe('Component Tests', () => {
  describe('Concept Management Detail Component', () => {
    let comp: ConceptDetailComponent;
    let fixture: ComponentFixture<ConceptDetailComponent>;
    const route = ({ data: of({ concept: new Concept(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [ConceptDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ConceptDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ConceptDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load concept on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.concept).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
