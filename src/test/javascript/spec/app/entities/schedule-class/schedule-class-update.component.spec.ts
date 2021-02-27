import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { ScheduleClassUpdateComponent } from 'app/entities/schedule-class/schedule-class-update.component';
import { ScheduleClassService } from 'app/entities/schedule-class/schedule-class.service';
import { ScheduleClass } from 'app/shared/model/schedule-class.model';

describe('Component Tests', () => {
  describe('ScheduleClass Management Update Component', () => {
    let comp: ScheduleClassUpdateComponent;
    let fixture: ComponentFixture<ScheduleClassUpdateComponent>;
    let service: ScheduleClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [ScheduleClassUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ScheduleClassUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScheduleClassUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScheduleClassService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ScheduleClass(123);
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
        const entity = new ScheduleClass();
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
