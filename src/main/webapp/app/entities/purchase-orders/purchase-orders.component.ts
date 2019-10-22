import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { AccountService } from 'app/core/auth/account.service';
import { PurchaseOrdersService } from './purchase-orders.service';

@Component({
  selector: 'jhi-purchase-orders',
  templateUrl: './purchase-orders.component.html'
})
export class PurchaseOrdersComponent implements OnInit, OnDestroy {
  purchaseOrders: IPurchaseOrders[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected purchaseOrdersService: PurchaseOrdersService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.purchaseOrdersService
      .query()
      .pipe(
        filter((res: HttpResponse<IPurchaseOrders[]>) => res.ok),
        map((res: HttpResponse<IPurchaseOrders[]>) => res.body)
      )
      .subscribe((res: IPurchaseOrders[]) => {
        this.purchaseOrders = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPurchaseOrders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPurchaseOrders) {
    return item.id;
  }

  registerChangeInPurchaseOrders() {
    this.eventSubscriber = this.eventManager.subscribe('purchaseOrdersListModification', response => this.loadAll());
  }
}
