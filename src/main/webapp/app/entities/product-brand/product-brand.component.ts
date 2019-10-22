import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IProductBrand } from 'app/shared/model/product-brand.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductBrandService } from './product-brand.service';

@Component({
  selector: 'jhi-product-brand',
  templateUrl: './product-brand.component.html'
})
export class ProductBrandComponent implements OnInit, OnDestroy {
  productBrands: IProductBrand[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productBrandService: ProductBrandService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productBrandService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductBrand[]>) => res.ok),
        map((res: HttpResponse<IProductBrand[]>) => res.body)
      )
      .subscribe((res: IProductBrand[]) => {
        this.productBrands = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductBrands();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductBrand) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInProductBrands() {
    this.eventSubscriber = this.eventManager.subscribe('productBrandListModification', response => this.loadAll());
  }
}
