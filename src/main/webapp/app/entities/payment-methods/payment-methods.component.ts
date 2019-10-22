import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentMethods } from 'app/shared/model/payment-methods.model';
import { AccountService } from 'app/core/auth/account.service';
import { PaymentMethodsService } from './payment-methods.service';

@Component({
  selector: 'jhi-payment-methods',
  templateUrl: './payment-methods.component.html'
})
export class PaymentMethodsComponent implements OnInit, OnDestroy {
  paymentMethods: IPaymentMethods[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected paymentMethodsService: PaymentMethodsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.paymentMethodsService
      .query()
      .pipe(
        filter((res: HttpResponse<IPaymentMethods[]>) => res.ok),
        map((res: HttpResponse<IPaymentMethods[]>) => res.body)
      )
      .subscribe((res: IPaymentMethods[]) => {
        this.paymentMethods = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPaymentMethods();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPaymentMethods) {
    return item.id;
  }

  registerChangeInPaymentMethods() {
    this.eventSubscriber = this.eventManager.subscribe('paymentMethodsListModification', response => this.loadAll());
  }
}
