import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DevpctTestModule } from '../../../test.module';
import { ClassIdentityDetailComponent } from 'app/entities/class-identity/class-identity-detail.component';
import { ClassIdentity } from 'app/shared/model/class-identity.model';

describe('Component Tests', () => {
  describe('ClassIdentity Management Detail Component', () => {
    let comp: ClassIdentityDetailComponent;
    let fixture: ComponentFixture<ClassIdentityDetailComponent>;
    const route = ({ data: of({ classIdentity: new ClassIdentity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DevpctTestModule],
        declarations: [ClassIdentityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ClassIdentityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassIdentityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classIdentity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classIdentity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
