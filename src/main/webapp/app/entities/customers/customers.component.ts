import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomers } from 'app/shared/model/customers.model';
import { AccountService } from 'app/core/auth/account.service';
import { CustomersService } from './customers.service';

@Component({
  selector: 'jhi-customers',
  templateUrl: './customers.component.html'
})
export class CustomersComponent implements OnInit, OnDestroy {
  customers: ICustomers[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected customersService: CustomersService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.customersService
      .query()
      .pipe(
        filter((res: HttpResponse<ICustomers[]>) => res.ok),
        map((res: HttpResponse<ICustomers[]>) => res.body)
      )
      .subscribe((res: ICustomers[]) => {
        this.customers = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCustomers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomers) {
    return item.id;
  }

  registerChangeInCustomers() {
    this.eventSubscriber = this.eventManager.subscribe('customersListModification', response => this.loadAll());
  }
}
