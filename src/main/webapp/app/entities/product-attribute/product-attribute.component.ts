import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAttribute } from 'app/shared/model/product-attribute.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductAttributeService } from './product-attribute.service';

@Component({
  selector: 'jhi-product-attribute',
  templateUrl: './product-attribute.component.html'
})
export class ProductAttributeComponent implements OnInit, OnDestroy {
  productAttributes: IProductAttribute[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productAttributeService: ProductAttributeService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productAttributeService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductAttribute[]>) => res.ok),
        map((res: HttpResponse<IProductAttribute[]>) => res.body)
      )
      .subscribe((res: IProductAttribute[]) => {
        this.productAttributes = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductAttributes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductAttribute) {
    return item.id;
  }

  registerChangeInProductAttributes() {
    this.eventSubscriber = this.eventManager.subscribe('productAttributeListModification', response => this.loadAll());
  }
}
