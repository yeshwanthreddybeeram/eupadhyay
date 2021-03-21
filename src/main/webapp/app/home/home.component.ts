import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { FormBuilder } from '@angular/forms';
import { StudentService } from 'app/entities/student/student.service';
import { IStudent, Student } from 'app/shared/model/student.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  isSaving = false;

  account: Account | null = null;
  authSubscription?: Subscription;
  aboutUs1 = '../../content/images/geometry.jpg';
  aboutUs2 = '../../content/images/geometry.jpg';
  aboutUs3 = '../../content/images/geometry.jpg';
  cards = [
    {
      title: 'Card Title 1',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
    {
      title: 'Card Title 2',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
    {
      title: 'Card Title 3',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
    {
      title: 'Card Title 4',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
    {
      title: 'Card Title 5',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
    {
      title: 'Card Title 6',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
    {
      title: 'Card Title 7',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
    {
      title: 'Card Title 8',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
    {
      title: 'Card Title 9',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
  ];
  slides: any = [[]];
  editForm = this.fb.group({
    name: [],
    email: [],
    mobilenumber: [],
    message: [],
  });

  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private fb: FormBuilder,
    private studentService: StudentService
  ) {}

  chunk(arr: any, chunkSize: any): any {
    const R = [];
    for (let i = 0, len = arr.length; i < len; i += chunkSize) {
      R.push(arr.slice(i, i + chunkSize));
    }
    return R;
  }
  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
    this.slides = this.chunk(this.cards, 3);
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  contactSave(): void {
    this.isSaving = true;
    const student = this.createFromForm();
    this.studentService.sendHomePageQuery(student);
  }
  // TODO: remove class number with more meaning ful variable.
  private createFromForm(): IStudent {
    return {
      ...new Student(),
      userName: this.editForm.get(['name'])!.value,
      classNumber: this.editForm.get(['message'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['mobilenumber'])!.value,
    };
  }

  // previousState(): void {
  //   window.history.back();
  // }
}
