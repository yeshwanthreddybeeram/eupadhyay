import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { ClassIdentityUpdateComponent } from 'app/entities/class-identity/class-identity-update.component';
import { ClassIdentityService } from 'app/entities/class-identity/class-identity.service';
import { ClassIdentity } from 'app/shared/model/class-identity.model';

describe('Component Tests', () => {
  describe('ClassIdentity Management Update Component', () => {
    let comp: ClassIdentityUpdateComponent;
    let fixture: ComponentFixture<ClassIdentityUpdateComponent>;
    let service: ClassIdentityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [ClassIdentityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ClassIdentityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassIdentityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClassIdentityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ClassIdentity(123);
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
        const entity = new ClassIdentity();
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
