import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { AssignmentQAUpdateComponent } from 'app/entities/assignment-qa/assignment-qa-update.component';
import { AssignmentQAService } from 'app/entities/assignment-qa/assignment-qa.service';
import { AssignmentQA } from 'app/shared/model/assignment-qa.model';

describe('Component Tests', () => {
  describe('AssignmentQA Management Update Component', () => {
    let comp: AssignmentQAUpdateComponent;
    let fixture: ComponentFixture<AssignmentQAUpdateComponent>;
    let service: AssignmentQAService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [AssignmentQAUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AssignmentQAUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AssignmentQAUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssignmentQAService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssignmentQA(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AssignmentQA();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
