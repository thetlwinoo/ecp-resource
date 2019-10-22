import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonEmailAddress } from 'app/shared/model/person-email-address.model';
import { AccountService } from 'app/core/auth/account.service';
import { PersonEmailAddressService } from './person-email-address.service';

@Component({
  selector: 'jhi-person-email-address',
  templateUrl: './person-email-address.component.html'
})
export class PersonEmailAddressComponent implements OnInit, OnDestroy {
  personEmailAddresses: IPersonEmailAddress[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected personEmailAddressService: PersonEmailAddressService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.personEmailAddressService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersonEmailAddress[]>) => res.ok),
        map((res: HttpResponse<IPersonEmailAddress[]>) => res.body)
      )
      .subscribe((res: IPersonEmailAddress[]) => {
        this.personEmailAddresses = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPersonEmailAddresses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonEmailAddress) {
    return item.id;
  }

  registerChangeInPersonEmailAddresses() {
    this.eventSubscriber = this.eventManager.subscribe('personEmailAddressListModification', response => this.loadAll());
  }
}
