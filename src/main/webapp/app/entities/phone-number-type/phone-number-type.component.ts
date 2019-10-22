import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPhoneNumberType } from 'app/shared/model/phone-number-type.model';
import { AccountService } from 'app/core/auth/account.service';
import { PhoneNumberTypeService } from './phone-number-type.service';

@Component({
  selector: 'jhi-phone-number-type',
  templateUrl: './phone-number-type.component.html'
})
export class PhoneNumberTypeComponent implements OnInit, OnDestroy {
  phoneNumberTypes: IPhoneNumberType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected phoneNumberTypeService: PhoneNumberTypeService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.phoneNumberTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IPhoneNumberType[]>) => res.ok),
        map((res: HttpResponse<IPhoneNumberType[]>) => res.body)
      )
      .subscribe((res: IPhoneNumberType[]) => {
        this.phoneNumberTypes = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPhoneNumberTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPhoneNumberType) {
    return item.id;
  }

  registerChangeInPhoneNumberTypes() {
    this.eventSubscriber = this.eventManager.subscribe('phoneNumberTypeListModification', response => this.loadAll());
  }
}
