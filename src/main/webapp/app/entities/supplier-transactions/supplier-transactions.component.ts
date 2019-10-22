import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierTransactions } from 'app/shared/model/supplier-transactions.model';
import { AccountService } from 'app/core/auth/account.service';
import { SupplierTransactionsService } from './supplier-transactions.service';

@Component({
  selector: 'jhi-supplier-transactions',
  templateUrl: './supplier-transactions.component.html'
})
export class SupplierTransactionsComponent implements OnInit, OnDestroy {
  supplierTransactions: ISupplierTransactions[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected supplierTransactionsService: SupplierTransactionsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.supplierTransactionsService
      .query()
      .pipe(
        filter((res: HttpResponse<ISupplierTransactions[]>) => res.ok),
        map((res: HttpResponse<ISupplierTransactions[]>) => res.body)
      )
      .subscribe((res: ISupplierTransactions[]) => {
        this.supplierTransactions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSupplierTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISupplierTransactions) {
    return item.id;
  }

  registerChangeInSupplierTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('supplierTransactionsListModification', response => this.loadAll());
  }
}
