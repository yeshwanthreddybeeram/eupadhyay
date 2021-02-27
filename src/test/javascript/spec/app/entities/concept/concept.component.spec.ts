import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DevpctTestModule } from '../../../test.module';
import { ConceptComponent } from 'app/entities/concept/concept.component';
import { ConceptService } from 'app/entities/concept/concept.service';
import { Concept } from 'app/shared/model/concept.model';

describe('Component Tests', () => {
  describe('Concept Management Component', () => {
    let comp: ConceptComponent;
    let fixture: ComponentFixture<ConceptComponent>;
    let service: ConceptService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [ConceptComponent],
      })
        .overrideTemplate(ConceptComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ConceptComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ConceptService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Concept(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.concepts && comp.concepts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
