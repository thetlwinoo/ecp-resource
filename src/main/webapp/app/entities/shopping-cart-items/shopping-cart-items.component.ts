import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';
import { AccountService } from 'app/core/auth/account.service';
import { ShoppingCartItemsService } from './shopping-cart-items.service';

@Component({
  selector: 'jhi-shopping-cart-items',
  templateUrl: './shopping-cart-items.component.html'
})
export class ShoppingCartItemsComponent implements OnInit, OnDestroy {
  shoppingCartItems: IShoppingCartItems[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected shoppingCartItemsService: ShoppingCartItemsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.shoppingCartItemsService
      .query()
      .pipe(
        filter((res: HttpResponse<IShoppingCartItems[]>) => res.ok),
        map((res: HttpResponse<IShoppingCartItems[]>) => res.body)
      )
      .subscribe((res: IShoppingCartItems[]) => {
        this.shoppingCartItems = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInShoppingCartItems();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IShoppingCartItems) {
    return item.id;
  }

  registerChangeInShoppingCartItems() {
    this.eventSubscriber = this.eventManager.subscribe('shoppingCartItemsListModification', response => this.loadAll());
  }
}
