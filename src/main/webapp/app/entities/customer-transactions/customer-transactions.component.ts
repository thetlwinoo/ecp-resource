import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerTransactions } from 'app/shared/model/customer-transactions.model';
import { AccountService } from 'app/core/auth/account.service';
import { CustomerTransactionsService } from './customer-transactions.service';

@Component({
  selector: 'jhi-customer-transactions',
  templateUrl: './customer-transactions.component.html'
})
export class CustomerTransactionsComponent implements OnInit, OnDestroy {
  customerTransactions: ICustomerTransactions[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected customerTransactionsService: CustomerTransactionsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.customerTransactionsService
      .query()
      .pipe(
        filter((res: HttpResponse<ICustomerTransactions[]>) => res.ok),
        map((res: HttpResponse<ICustomerTransactions[]>) => res.body)
      )
      .subscribe((res: ICustomerTransactions[]) => {
        this.customerTransactions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCustomerTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomerTransactions) {
    return item.id;
  }

  registerChangeInCustomerTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('customerTransactionsListModification', response => this.loadAll());
  }
}
