import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { VideoLinkUpdateComponent } from 'app/entities/video-link/video-link-update.component';
import { VideoLinkService } from 'app/entities/video-link/video-link.service';
import { VideoLink } from 'app/shared/model/video-link.model';

describe('Component Tests', () => {
  describe('VideoLink Management Update Component', () => {
    let comp: VideoLinkUpdateComponent;
    let fixture: ComponentFixture<VideoLinkUpdateComponent>;
    let service: VideoLinkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [VideoLinkUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(VideoLinkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VideoLinkUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VideoLinkService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VideoLink(123);
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
        const entity = new VideoLink();
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
