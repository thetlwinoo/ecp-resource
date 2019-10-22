import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { AccountService } from 'app/core/auth/account.service';
import { ShoppingCartsService } from './shopping-carts.service';

@Component({
  selector: 'jhi-shopping-carts',
  templateUrl: './shopping-carts.component.html'
})
export class ShoppingCartsComponent implements OnInit, OnDestroy {
  shoppingCarts: IShoppingCarts[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected shoppingCartsService: ShoppingCartsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.shoppingCartsService
      .query()
      .pipe(
        filter((res: HttpResponse<IShoppingCarts[]>) => res.ok),
        map((res: HttpResponse<IShoppingCarts[]>) => res.body)
      )
      .subscribe((res: IShoppingCarts[]) => {
        this.shoppingCarts = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInShoppingCarts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShoppingCarts) {
    return item.id;
  }

  registerChangeInShoppingCarts() {
    this.eventSubscriber = this.eventManager.subscribe('shoppingCartsListModification', response => this.loadAll());
  }
}
