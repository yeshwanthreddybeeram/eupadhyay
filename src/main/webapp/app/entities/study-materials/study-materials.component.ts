import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStudyMaterials } from 'app/shared/model/study-materials.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { StudyMaterialsService } from './study-materials.service';
import { StudyMaterialsDeleteDialogComponent } from './study-materials-delete-dialog.component';
import { FolderType } from 'app/shared/model/enumerations/folder-type.model';

@Component({
  selector: 'jhi-study-materials',
  templateUrl: './study-materials.component.html',
  styleUrls: ['study-materials.scss'],
})
export class StudyMaterialsComponent implements OnInit, OnDestroy {
  studyMaterials: IStudyMaterials[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  folderType?: FolderType = FolderType.VIDEOS;

  constructor(
    protected studyMaterialsService: StudyMaterialsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.studyMaterials = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.studyMaterialsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IStudyMaterials[]>) => this.paginateStudyMaterials(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.studyMaterials = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStudyMaterials();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStudyMaterials): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStudyMaterials(): void {
    this.eventSubscriber = this.eventManager.subscribe('studyMaterialsListModification', () => this.reset());
  }

  delete(studyMaterials: IStudyMaterials): void {
    const modalRef = this.modalService.open(StudyMaterialsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.studyMaterials = studyMaterials;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateStudyMaterials(data: IStudyMaterials[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.studyMaterials.push(data[i]);
      }
    }
  }

  dispalyVideos(): void {
    this.folderType = FolderType.VIDEOS;
  }
  displayAssignments(): void {
    this.folderType = FolderType.ASSIGNMENTS;
  }
  displayOthers(): void {
    this.folderType = FolderType.OTHERS;
  }

  checkFolderType(studyMaterials: IStudyMaterials): boolean {
    return studyMaterials.foldertype === this.folderType;
  }
}
