import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IDangerousGoods } from 'app/shared/model/dangerous-goods.model';
import { AccountService } from 'app/core/auth/account.service';
import { DangerousGoodsService } from './dangerous-goods.service';

@Component({
  selector: 'jhi-dangerous-goods',
  templateUrl: './dangerous-goods.component.html'
})
export class DangerousGoodsComponent implements OnInit, OnDestroy {
  dangerousGoods: IDangerousGoods[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dangerousGoodsService: DangerousGoodsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dangerousGoodsService
      .query()
      .pipe(
        filter((res: HttpResponse<IDangerousGoods[]>) => res.ok),
        map((res: HttpResponse<IDangerousGoods[]>) => res.body)
      )
      .subscribe((res: IDangerousGoods[]) => {
        this.dangerousGoods = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDangerousGoods();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDangerousGoods) {
    return item.id;
  }

  registerChangeInDangerousGoods() {
    this.eventSubscriber = this.eventManager.subscribe('dangerousGoodsListModification', response => this.loadAll());
  }
}
