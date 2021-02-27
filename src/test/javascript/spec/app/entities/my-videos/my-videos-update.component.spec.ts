import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { MyVideosUpdateComponent } from 'app/entities/my-videos/my-videos-update.component';
import { MyVideosService } from 'app/entities/my-videos/my-videos.service';
import { MyVideos } from 'app/shared/model/my-videos.model';

describe('Component Tests', () => {
  describe('MyVideos Management Update Component', () => {
    let comp: MyVideosUpdateComponent;
    let fixture: ComponentFixture<MyVideosUpdateComponent>;
    let service: MyVideosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [MyVideosUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MyVideosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MyVideosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MyVideosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MyVideos(123);
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
        const entity = new MyVideos();
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
