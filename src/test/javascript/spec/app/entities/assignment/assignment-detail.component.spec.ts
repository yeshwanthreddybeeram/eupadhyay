import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { DevpctTestModule } from '../../../test.module';
import { AssignmentDetailComponent } from 'app/entities/assignment/assignment-detail.component';
import { Assignment } from 'app/shared/model/assignment.model';

describe('Component Tests', () => {
  describe('Assignment Management Detail Component', () => {
    let comp: AssignmentDetailComponent;
    let fixture: ComponentFixture<AssignmentDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ assignment: new Assignment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [AssignmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AssignmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssignmentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load assignment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assignment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
