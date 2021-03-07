import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IStudent, Student } from 'app/shared/model/student.model';
import { StudentService } from './student.service';
import { IMyVideos } from 'app/shared/model/my-videos.model';
import { MyVideosService } from 'app/entities/my-videos/my-videos.service';

@Component({
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html',
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
  myvideos: IMyVideos[] = [];

  editForm = this.fb.group({
    id: [],
    userName: [null, [Validators.required]],
    fullName: [null, [Validators.required]],
    email: [null, [Validators.required]],
    phoneNumber: [null, [Validators.required]],
    joindate: [],
    classNumber: [],
    myVideos: [],
  });

  constructor(
    protected studentService: StudentService,
    protected myVideosService: MyVideosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      if (!student.id) {
        const today = moment().startOf('day');
        student.joindate = today;
      }

      this.updateForm(student);

      this.myVideosService.query().subscribe((res: HttpResponse<IMyVideos[]>) => (this.myvideos = res.body || []));
    });
  }

  updateForm(student: IStudent): void {
    this.editForm.patchValue({
      id: student.id,
      userName: student.userName,
      fullName: student.fullName,
      email: student.email,
      phoneNumber: student.phoneNumber,
      joindate: student.joindate ? student.joindate.format(DATE_TIME_FORMAT) : null,
      classNumber: student.classNumber,
      myVideos: student.myVideos,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    if (student.id !== undefined) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  private createFromForm(): IStudent {
    return {
      ...new Student(),
      id: this.editForm.get(['id'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      joindate: this.editForm.get(['joindate'])!.value ? moment(this.editForm.get(['joindate'])!.value, DATE_TIME_FORMAT) : undefined,
      classNumber: this.editForm.get(['classNumber'])!.value,
      myVideos: this.editForm.get(['myVideos'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IMyVideos): any {
    return item.id;
  }

  getSelected(selectedVals: IMyVideos[], option: IMyVideos): IMyVideos {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
