import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';
import { AccountService } from 'app/core/auth/account.service';
import { PurchaseOrderLinesService } from './purchase-order-lines.service';

@Component({
  selector: 'jhi-purchase-order-lines',
  templateUrl: './purchase-order-lines.component.html'
})
export class PurchaseOrderLinesComponent implements OnInit, OnDestroy {
  purchaseOrderLines: IPurchaseOrderLines[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected purchaseOrderLinesService: PurchaseOrderLinesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.purchaseOrderLinesService
      .query()
      .pipe(
        filter((res: HttpResponse<IPurchaseOrderLines[]>) => res.ok),
        map((res: HttpResponse<IPurchaseOrderLines[]>) => res.body)
      )
      .subscribe((res: IPurchaseOrderLines[]) => {
        this.purchaseOrderLines = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPurchaseOrderLines();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPurchaseOrderLines) {
    return item.id;
  }

  registerChangeInPurchaseOrderLines() {
    this.eventSubscriber = this.eventManager.subscribe('purchaseOrderLinesListModification', response => this.loadAll());
  }
}
