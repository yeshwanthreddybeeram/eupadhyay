import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { StudyMaterialsDetailComponent } from 'app/entities/study-materials/study-materials-detail.component';
import { StudyMaterials } from 'app/shared/model/study-materials.model';

describe('Component Tests', () => {
  describe('StudyMaterials Management Detail Component', () => {
    let comp: StudyMaterialsDetailComponent;
    let fixture: ComponentFixture<StudyMaterialsDetailComponent>;
    const route = ({ data: of({ studyMaterials: new StudyMaterials(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [StudyMaterialsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(StudyMaterialsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StudyMaterialsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load studyMaterials on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.studyMaterials).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
