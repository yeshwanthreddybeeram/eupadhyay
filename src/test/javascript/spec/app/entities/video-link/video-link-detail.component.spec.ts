import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { VideoLinkDetailComponent } from 'app/entities/video-link/video-link-detail.component';
import { VideoLink } from 'app/shared/model/video-link.model';

describe('Component Tests', () => {
  describe('VideoLink Management Detail Component', () => {
    let comp: VideoLinkDetailComponent;
    let fixture: ComponentFixture<VideoLinkDetailComponent>;
    const route = ({ data: of({ videoLink: new VideoLink(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [VideoLinkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VideoLinkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VideoLinkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load videoLink on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.videoLink).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
