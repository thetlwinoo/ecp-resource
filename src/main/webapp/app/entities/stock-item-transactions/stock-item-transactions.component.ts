import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItemTransactions } from 'app/shared/model/stock-item-transactions.model';
import { AccountService } from 'app/core/auth/account.service';
import { StockItemTransactionsService } from './stock-item-transactions.service';

@Component({
  selector: 'jhi-stock-item-transactions',
  templateUrl: './stock-item-transactions.component.html'
})
export class StockItemTransactionsComponent implements OnInit, OnDestroy {
  stockItemTransactions: IStockItemTransactions[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected stockItemTransactionsService: StockItemTransactionsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.stockItemTransactionsService
      .query()
      .pipe(
        filter((res: HttpResponse<IStockItemTransactions[]>) => res.ok),
        map((res: HttpResponse<IStockItemTransactions[]>) => res.body)
      )
      .subscribe((res: IStockItemTransactions[]) => {
        this.stockItemTransactions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStockItemTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStockItemTransactions) {
    return item.id;
  }

  registerChangeInStockItemTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('stockItemTransactionsListModification', response => this.loadAll());
  }
}
