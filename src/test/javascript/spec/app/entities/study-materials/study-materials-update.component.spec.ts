import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { StudyMaterialsUpdateComponent } from 'app/entities/study-materials/study-materials-update.component';
import { StudyMaterialsService } from 'app/entities/study-materials/study-materials.service';
import { StudyMaterials } from 'app/shared/model/study-materials.model';

describe('Component Tests', () => {
  describe('StudyMaterials Management Update Component', () => {
    let comp: StudyMaterialsUpdateComponent;
    let fixture: ComponentFixture<StudyMaterialsUpdateComponent>;
    let service: StudyMaterialsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [StudyMaterialsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(StudyMaterialsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StudyMaterialsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StudyMaterialsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StudyMaterials(123);
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
        const entity = new StudyMaterials();
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
