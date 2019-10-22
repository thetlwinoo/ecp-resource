import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICurrencyRate } from 'app/shared/model/currency-rate.model';
import { AccountService } from 'app/core/auth/account.service';
import { CurrencyRateService } from './currency-rate.service';

@Component({
  selector: 'jhi-currency-rate',
  templateUrl: './currency-rate.component.html'
})
export class CurrencyRateComponent implements OnInit, OnDestroy {
  currencyRates: ICurrencyRate[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected currencyRateService: CurrencyRateService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.currencyRateService
      .query()
      .pipe(
        filter((res: HttpResponse<ICurrencyRate[]>) => res.ok),
        map((res: HttpResponse<ICurrencyRate[]>) => res.body)
      )
      .subscribe((res: ICurrencyRate[]) => {
        this.currencyRates = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCurrencyRates();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICurrencyRate) {
    return item.id;
  }

  registerChangeInCurrencyRates() {
    this.eventSubscriber = this.eventManager.subscribe('currencyRateListModification', response => this.loadAll());
  }
}
