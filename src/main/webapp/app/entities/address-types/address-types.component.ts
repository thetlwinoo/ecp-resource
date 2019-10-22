import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AccountService } from 'app/core/auth/account.service';
import { AddressTypesService } from './address-types.service';

@Component({
  selector: 'jhi-address-types',
  templateUrl: './address-types.component.html'
})
export class AddressTypesComponent implements OnInit, OnDestroy {
  addressTypes: IAddressTypes[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected addressTypesService: AddressTypesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.addressTypesService
      .query()
      .pipe(
        filter((res: HttpResponse<IAddressTypes[]>) => res.ok),
        map((res: HttpResponse<IAddressTypes[]>) => res.body)
      )
      .subscribe((res: IAddressTypes[]) => {
        this.addressTypes = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAddressTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAddressTypes) {
    return item.id;
  }

  registerChangeInAddressTypes() {
    this.eventSubscriber = this.eventManager.subscribe('addressTypesListModification', response => this.loadAll());
  }
}
