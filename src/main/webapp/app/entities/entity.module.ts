import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.DevpctDepartmentModule),
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.DevpctStudentModule),
      },
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.DevpctEmployeeModule),
      },
      {
        path: 'concept',
        loadChildren: () => import('./concept/concept.module').then(m => m.DevpctConceptModule),
      },
      {
        path: 'video-link',
        loadChildren: () => import('./video-link/video-link.module').then(m => m.DevpctVideoLinkModule),
      },
      {
        path: 'schedule-class',
        loadChildren: () => import('./schedule-class/schedule-class.module').then(m => m.DevpctScheduleClassModule),
      },
      {
        path: 'my-videos',
        loadChildren: () => import('./my-videos/my-videos.module').then(m => m.DevpctMyVideosModule),
      },
      {
        path: 'assignment',
        loadChildren: () => import('./assignment/assignment.module').then(m => m.DevpctAssignmentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DevpctEntityModule {}
