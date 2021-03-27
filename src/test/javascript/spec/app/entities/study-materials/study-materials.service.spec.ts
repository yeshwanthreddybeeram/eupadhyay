import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { StudyMaterialsService } from 'app/entities/study-materials/study-materials.service';
import { IStudyMaterials, StudyMaterials } from 'app/shared/model/study-materials.model';
import { FolderType } from 'app/shared/model/enumerations/folder-type.model';

describe('Service Tests', () => {
  describe('StudyMaterials Service', () => {
    let injector: TestBed;
    let service: StudyMaterialsService;
    let httpMock: HttpTestingController;
    let elemDefault: IStudyMaterials;
    let expectedResult: IStudyMaterials | IStudyMaterials[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StudyMaterialsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new StudyMaterials(0, 'AAAAAAA', 'AAAAAAA', FolderType.VIDEOS);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a StudyMaterials', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new StudyMaterials()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StudyMaterials', () => {
        const returnedFromService = Object.assign(
          {
            folderName: 'BBBBBB',
            folderDescription: 'BBBBBB',
            foldertype: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StudyMaterials', () => {
        const returnedFromService = Object.assign(
          {
            folderName: 'BBBBBB',
            folderDescription: 'BBBBBB',
            foldertype: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a StudyMaterials', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
