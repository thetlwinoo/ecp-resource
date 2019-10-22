import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { AccountService } from 'app/core/auth/account.service';
import { PaymentTransactionsService } from './payment-transactions.service';

@Component({
  selector: 'jhi-payment-transactions',
  templateUrl: './payment-transactions.component.html'
})
export class PaymentTransactionsComponent implements OnInit, OnDestroy {
  paymentTransactions: IPaymentTransactions[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected paymentTransactionsService: PaymentTransactionsService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.paymentTransactionsService
      .query()
      .pipe(
        filter((res: HttpResponse<IPaymentTransactions[]>) => res.ok),
        map((res: HttpResponse<IPaymentTransactions[]>) => res.body)
      )
      .subscribe((res: IPaymentTransactions[]) => {
        this.paymentTransactions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPaymentTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaymentTransactions) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPaymentTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('paymentTransactionsListModification', response => this.loadAll());
  }
}
