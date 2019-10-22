import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductOption } from 'app/shared/model/product-option.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductOptionService } from './product-option.service';

@Component({
  selector: 'jhi-product-option',
  templateUrl: './product-option.component.html'
})
export class ProductOptionComponent implements OnInit, OnDestroy {
  productOptions: IProductOption[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productOptionService: ProductOptionService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productOptionService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductOption[]>) => res.ok),
        map((res: HttpResponse<IProductOption[]>) => res.body)
      )
      .subscribe((res: IProductOption[]) => {
        this.productOptions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductOptions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductOption) {
    return item.id;
  }

  registerChangeInProductOptions() {
    this.eventSubscriber = this.eventManager.subscribe('productOptionListModification', response => this.loadAll());
  }
}
