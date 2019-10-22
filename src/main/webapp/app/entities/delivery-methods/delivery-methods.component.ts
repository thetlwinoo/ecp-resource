import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { AccountService } from 'app/core/auth/account.service';
import { DeliveryMethodsService } from './delivery-methods.service';

@Component({
  selector: 'jhi-delivery-methods',
  templateUrl: './delivery-methods.component.html'
})
export class DeliveryMethodsComponent implements OnInit, OnDestroy {
  deliveryMethods: IDeliveryMethods[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected deliveryMethodsService: DeliveryMethodsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.deliveryMethodsService
      .query()
      .pipe(
        filter((res: HttpResponse<IDeliveryMethods[]>) => res.ok),
        map((res: HttpResponse<IDeliveryMethods[]>) => res.body)
      )
      .subscribe((res: IDeliveryMethods[]) => {
        this.deliveryMethods = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDeliveryMethods();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDeliveryMethods) {
    return item.id;
  }

  registerChangeInDeliveryMethods() {
    this.eventSubscriber = this.eventManager.subscribe('deliveryMethodsListModification', response => this.loadAll());
  }
}
