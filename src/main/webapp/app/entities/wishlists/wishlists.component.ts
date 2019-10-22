import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IWishlists } from 'app/shared/model/wishlists.model';
import { AccountService } from 'app/core/auth/account.service';
import { WishlistsService } from './wishlists.service';

@Component({
  selector: 'jhi-wishlists',
  templateUrl: './wishlists.component.html'
})
export class WishlistsComponent implements OnInit, OnDestroy {
  wishlists: IWishlists[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected wishlistsService: WishlistsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.wishlistsService
      .query()
      .pipe(
        filter((res: HttpResponse<IWishlists[]>) => res.ok),
        map((res: HttpResponse<IWishlists[]>) => res.body)
      )
      .subscribe((res: IWishlists[]) => {
        this.wishlists = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInWishlists();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWishlists) {
    return item.id;
  }

  registerChangeInWishlists() {
    this.eventSubscriber = this.eventManager.subscribe('wishlistsListModification', response => this.loadAll());
  }
}
