import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DevpctTestModule } from '../../../test.module';
import { MyVideosComponent } from 'app/entities/my-videos/my-videos.component';
import { MyVideosService } from 'app/entities/my-videos/my-videos.service';
import { MyVideos } from 'app/shared/model/my-videos.model';

describe('Component Tests', () => {
  describe('MyVideos Management Component', () => {
    let comp: MyVideosComponent;
    let fixture: ComponentFixture<MyVideosComponent>;
    let service: MyVideosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [MyVideosComponent],
      })
        .overrideTemplate(MyVideosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MyVideosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MyVideosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MyVideos(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.myVideos && comp.myVideos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
