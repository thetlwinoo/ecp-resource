import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonPhone } from 'app/shared/model/person-phone.model';
import { AccountService } from 'app/core/auth/account.service';
import { PersonPhoneService } from './person-phone.service';

@Component({
  selector: 'jhi-person-phone',
  templateUrl: './person-phone.component.html'
})
export class PersonPhoneComponent implements OnInit, OnDestroy {
  personPhones: IPersonPhone[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected personPhoneService: PersonPhoneService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.personPhoneService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersonPhone[]>) => res.ok),
        map((res: HttpResponse<IPersonPhone[]>) => res.body)
      )
      .subscribe((res: IPersonPhone[]) => {
        this.personPhones = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPersonPhones();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonPhone) {
    return item.id;
  }

  registerChangeInPersonPhones() {
    this.eventSubscriber = this.eventManager.subscribe('personPhoneListModification', response => this.loadAll());
  }
}
