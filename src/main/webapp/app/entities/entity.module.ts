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
      {
        path: 'assignment-qa',
        loadChildren: () => import('./assignment-qa/assignment-qa.module').then(m => m.DevpctAssignmentQAModule),
      },
      {
        path: 'class-identity',
        loadChildren: () => import('./class-identity/class-identity.module').then(m => m.DevpctClassIdentityModule),
      },
      {
        path: 'guest',
        loadChildren: () => import('./guest/guest.module').then(m => m.DevpctGuestModule),
      },
      {
        path: 'study-materials',
        loadChildren: () => import('./study-materials/study-materials.module').then(m => m.DevpctStudyMaterialsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DevpctEntityModule {}
