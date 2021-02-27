import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { ScheduleClassDetailComponent } from 'app/entities/schedule-class/schedule-class-detail.component';
import { ScheduleClass } from 'app/shared/model/schedule-class.model';

describe('Component Tests', () => {
  describe('ScheduleClass Management Detail Component', () => {
    let comp: ScheduleClassDetailComponent;
    let fixture: ComponentFixture<ScheduleClassDetailComponent>;
    const route = ({ data: of({ scheduleClass: new ScheduleClass(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [ScheduleClassDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ScheduleClassDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ScheduleClassDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load scheduleClass on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.scheduleClass).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
