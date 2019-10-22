import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductCatalog } from 'app/shared/model/product-catalog.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductCatalogService } from './product-catalog.service';

@Component({
  selector: 'jhi-product-catalog',
  templateUrl: './product-catalog.component.html'
})
export class ProductCatalogComponent implements OnInit, OnDestroy {
  productCatalogs: IProductCatalog[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productCatalogService: ProductCatalogService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productCatalogService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductCatalog[]>) => res.ok),
        map((res: HttpResponse<IProductCatalog[]>) => res.body)
      )
      .subscribe((res: IProductCatalog[]) => {
        this.productCatalogs = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductCatalogs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductCatalog) {
    return item.id;
  }

  registerChangeInProductCatalogs() {
    this.eventSubscriber = this.eventManager.subscribe('productCatalogListModification', response => this.loadAll());
  }
}
