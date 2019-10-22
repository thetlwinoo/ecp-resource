import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IShipMethod } from 'app/shared/model/ship-method.model';
import { AccountService } from 'app/core/auth/account.service';
import { ShipMethodService } from './ship-method.service';

@Component({
  selector: 'jhi-ship-method',
  templateUrl: './ship-method.component.html'
})
export class ShipMethodComponent implements OnInit, OnDestroy {
  shipMethods: IShipMethod[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected shipMethodService: ShipMethodService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.shipMethodService
      .query()
      .pipe(
        filter((res: HttpResponse<IShipMethod[]>) => res.ok),
        map((res: HttpResponse<IShipMethod[]>) => res.body)
      )
      .subscribe((res: IShipMethod[]) => {
        this.shipMethods = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInShipMethods();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShipMethod) {
    return item.id;
  }

  registerChangeInShipMethods() {
    this.eventSubscriber = this.eventManager.subscribe('shipMethodListModification', response => this.loadAll());
  }
}
