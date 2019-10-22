import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IBuyingGroups } from 'app/shared/model/buying-groups.model';
import { AccountService } from 'app/core/auth/account.service';
import { BuyingGroupsService } from './buying-groups.service';

@Component({
  selector: 'jhi-buying-groups',
  templateUrl: './buying-groups.component.html'
})
export class BuyingGroupsComponent implements OnInit, OnDestroy {
  buyingGroups: IBuyingGroups[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected buyingGroupsService: BuyingGroupsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.buyingGroupsService
      .query()
      .pipe(
        filter((res: HttpResponse<IBuyingGroups[]>) => res.ok),
        map((res: HttpResponse<IBuyingGroups[]>) => res.body)
      )
      .subscribe((res: IBuyingGroups[]) => {
        this.buyingGroups = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBuyingGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBuyingGroups) {
    return item.id;
  }

  registerChangeInBuyingGroups() {
    this.eventSubscriber = this.eventManager.subscribe('buyingGroupsListModification', response => this.loadAll());
  }
}
