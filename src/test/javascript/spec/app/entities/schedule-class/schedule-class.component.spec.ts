import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DevpctTestModule } from '../../../test.module';
import { ScheduleClassComponent } from 'app/entities/schedule-class/schedule-class.component';
import { ScheduleClassService } from 'app/entities/schedule-class/schedule-class.service';
import { ScheduleClass } from 'app/shared/model/schedule-class.model';

describe('Component Tests', () => {
  describe('ScheduleClass Management Component', () => {
    let comp: ScheduleClassComponent;
    let fixture: ComponentFixture<ScheduleClassComponent>;
    let service: ScheduleClassService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [ScheduleClassComponent],
      })
        .overrideTemplate(ScheduleClassComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScheduleClassComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScheduleClassService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ScheduleClass(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.scheduleClasses && comp.scheduleClasses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
