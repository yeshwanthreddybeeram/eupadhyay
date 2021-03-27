import { IVideoLink } from 'app/shared/model/video-link.model';
import { FolderType } from 'app/shared/model/enumerations/folder-type.model';

export interface IStudyMaterials {
  id?: number;
  folderName?: string;
  folderDescription?: string;
  foldertype?: FolderType;
  videoLinks?: IVideoLink[];
}

export class StudyMaterials implements IStudyMaterials {
  constructor(
    public id?: number,
    public folderName?: string,
    public folderDescription?: string,
    public foldertype?: FolderType,
    public videoLinks?: IVideoLink[]
  ) {}
}
