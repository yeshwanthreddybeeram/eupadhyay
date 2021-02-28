import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IScheduleClass, ScheduleClass } from 'app/shared/model/schedule-class.model';
import { ScheduleClassService } from './schedule-class.service';
import { IStudent } from 'app/shared/model/student.model';
import { StudentService } from 'app/entities/student/student.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

type SelectableEntity = IStudent | IEmployee;

@Component({
  selector: 'jhi-schedule-class-update',
  templateUrl: './schedule-class-update.component.html',
})
export class ScheduleClassUpdateComponent implements OnInit {
  isSaving = false;
  students: IStudent[] = [];
  employees: IEmployee[] = [];

  editForm = this.fb.group({
    id: [],
    schedulelink: [null, [Validators.required]],
    scheduleTime: [null, [Validators.required]],
    studentname: [null, [Validators.required]],
    employeename: [null, [Validators.required]],
    videolink: [],
    complete: [],
    remove: [],
    students: [],
    employees: [],
  });

  constructor(
    protected scheduleClassService: ScheduleClassService,
    protected studentService: StudentService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scheduleClass }) => {
      if (!scheduleClass.id) {
        const today = moment().startOf('day');
        scheduleClass.scheduleTime = today;
      }

      this.updateForm(scheduleClass);

      this.studentService.query().subscribe((res: HttpResponse<IStudent[]>) => (this.students = res.body || []));

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    });
  }

  updateForm(scheduleClass: IScheduleClass): void {
    this.editForm.patchValue({
      id: scheduleClass.id,
      schedulelink: scheduleClass.schedulelink,
      scheduleTime: scheduleClass.scheduleTime ? scheduleClass.scheduleTime.format(DATE_TIME_FORMAT) : null,
      studentname: scheduleClass.studentname,
      employeename: scheduleClass.employeename,
      videolink: scheduleClass.videolink,
      complete: scheduleClass.complete,
      remove: scheduleClass.remove,
      students: scheduleClass.students,
      employees: scheduleClass.employees,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scheduleClass = this.createFromForm();
    if (scheduleClass.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleClassService.update(scheduleClass));
    } else {
      this.subscribeToSaveResponse(this.scheduleClassService.create(scheduleClass));
    }
  }

  private createFromForm(): IScheduleClass {
    return {
      ...new ScheduleClass(),
      id: this.editForm.get(['id'])!.value,
      schedulelink: this.editForm.get(['schedulelink'])!.value,
      scheduleTime: this.editForm.get(['scheduleTime'])!.value
        ? moment(this.editForm.get(['scheduleTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      studentname: this.editForm.get(['studentname'])!.value,
      employeename: this.editForm.get(['employeename'])!.value,
      videolink: this.editForm.get(['videolink'])!.value,
      complete: false,
      remove: false,
      students: this.editForm.get(['students'])!.value,
      employees: this.editForm.get(['employees'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScheduleClass>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
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
