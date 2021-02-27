import { IConcept } from 'app/shared/model/concept.model';

export interface IVideoLink {
  id?: number;
  classlink?: string;
  concepts?: IConcept[];
}

export class VideoLink implements IVideoLink {
  constructor(public id?: number, public classlink?: string, public concepts?: IConcept[]) {}
}
