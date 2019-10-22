import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IProductCategory } from 'app/shared/model/product-category.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductCategoryService } from './product-category.service';

@Component({
  selector: 'jhi-product-category',
  templateUrl: './product-category.component.html'
})
export class ProductCategoryComponent implements OnInit, OnDestroy {
  productCategories: IProductCategory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productCategoryService: ProductCategoryService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productCategoryService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductCategory[]>) => res.ok),
        map((res: HttpResponse<IProductCategory[]>) => res.body)
      )
      .subscribe((res: IProductCategory[]) => {
        this.productCategories = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductCategories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductCategory) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInProductCategories() {
    this.eventSubscriber = this.eventManager.subscribe('productCategoryListModification', response => this.loadAll());
  }
}
