import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DevpctTestModule } from '../../../test.module';
import { VideoLinkComponent } from 'app/entities/video-link/video-link.component';
import { VideoLinkService } from 'app/entities/video-link/video-link.service';
import { VideoLink } from 'app/shared/model/video-link.model';

describe('Component Tests', () => {
  describe('VideoLink Management Component', () => {
    let comp: VideoLinkComponent;
    let fixture: ComponentFixture<VideoLinkComponent>;
    let service: VideoLinkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [VideoLinkComponent],
      })
        .overrideTemplate(VideoLinkComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VideoLinkComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VideoLinkService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new VideoLink(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.videoLinks && comp.videoLinks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
