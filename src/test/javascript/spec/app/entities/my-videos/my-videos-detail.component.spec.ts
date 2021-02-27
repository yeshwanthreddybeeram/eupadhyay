import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { MyVideosDetailComponent } from 'app/entities/my-videos/my-videos-detail.component';
import { MyVideos } from 'app/shared/model/my-videos.model';

describe('Component Tests', () => {
  describe('MyVideos Management Detail Component', () => {
    let comp: MyVideosDetailComponent;
    let fixture: ComponentFixture<MyVideosDetailComponent>;
    const route = ({ data: of({ myVideos: new MyVideos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [MyVideosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MyVideosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MyVideosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load myVideos on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.myVideos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
