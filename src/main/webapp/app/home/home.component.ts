import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Guest } from 'app/shared/model/guest.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { GuestUpdateComponent } from 'app/entities/guest/guest-update.component';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  isSaving = false;

  guest: Guest | null = null;
  account: Account | null = null;
  authSubscription?: Subscription;
  aboutUs1 = '../../content/images/zoomlearning.jpg';
  aboutUs2 = '../../content/images/conceptlearning.png';
  aboutUs3 = '../../content/images/assignment.png';
  cards = [
    {
      title: 'Geometry',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/geometry.jpg',
    },
    {
      title: 'Trigonometry',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/trignometry.jpg',
    },
    {
      title: 'Statistics',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/statistics.png',
    },
    {
      title: 'Mechanics',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/mechanics.png',
    },
    {
      title: 'Calculus',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/calculus.jpg',
    },
    {
      title: 'Gravity',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/gravity.jpg',
    },
    {
      title: 'Electro Magnetic Waves',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/electromagneticwaves.jpg',
    },
    {
      title: 'Algebra',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/algebra.jpg',
    },
    {
      title: 'Electricity',
      description: 'Some quick example text to build on the card title and make up the bulk of the card content',
      buttonText: 'Button',
      img: '../../content/images/electricity.png',
    },
  ];
  slides: any = [[]];

  constructor(private accountService: AccountService, private loginModalService: LoginModalService, protected modalService: NgbModal) {}

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
    this.guest = new Guest();
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

  updateGuestQuery(): void {
    const modalRef = this.modalService.open(GuestUpdateComponent, { size: 'lg', backdrop: 'static' });
  }
}
