import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICurrency } from 'app/shared/model/currency.model';
import { AccountService } from 'app/core/auth/account.service';
import { CurrencyService } from './currency.service';

@Component({
  selector: 'jhi-currency',
  templateUrl: './currency.component.html'
})
export class CurrencyComponent implements OnInit, OnDestroy {
  currencies: ICurrency[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected currencyService: CurrencyService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.currencyService
      .query()
      .pipe(
        filter((res: HttpResponse<ICurrency[]>) => res.ok),
        map((res: HttpResponse<ICurrency[]>) => res.body)
      )
      .subscribe((res: ICurrency[]) => {
        this.currencies = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCurrencies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICurrency) {
    return item.id;
  }

  registerChangeInCurrencies() {
    this.eventSubscriber = this.eventManager.subscribe('currencyListModification', response => this.loadAll());
  }
}
