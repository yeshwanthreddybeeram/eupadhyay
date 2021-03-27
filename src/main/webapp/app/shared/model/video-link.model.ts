import { IConcept } from 'app/shared/model/concept.model';
import { IStudyMaterials } from 'app/shared/model/study-materials.model';

export interface IVideoLink {
  id?: number;
  classlink?: string;
  linkName?: string;
  linkDescription?: string;
  submitLink?: string;
  concepts?: IConcept[];
  studyMaterials?: IStudyMaterials[];
}

export class VideoLink implements IVideoLink {
  constructor(
    public id?: number,
    public classlink?: string,
    public linkName?: string,
    public linkDescription?: string,
    public submitLink?: string,
    public concepts?: IConcept[],
    public studyMaterials?: IStudyMaterials[]
  ) {}
}
