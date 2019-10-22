import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductSetDetails } from 'app/shared/model/product-set-details.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductSetDetailsService } from './product-set-details.service';

@Component({
  selector: 'jhi-product-set-details',
  templateUrl: './product-set-details.component.html'
})
export class ProductSetDetailsComponent implements OnInit, OnDestroy {
  productSetDetails: IProductSetDetails[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productSetDetailsService: ProductSetDetailsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productSetDetailsService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductSetDetails[]>) => res.ok),
        map((res: HttpResponse<IProductSetDetails[]>) => res.body)
      )
      .subscribe((res: IProductSetDetails[]) => {
        this.productSetDetails = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductSetDetails();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductSetDetails) {
    return item.id;
  }

  registerChangeInProductSetDetails() {
    this.eventSubscriber = this.eventManager.subscribe('productSetDetailsListModification', response => this.loadAll());
  }
}
