import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductAttributeSetService } from './product-attribute-set.service';

@Component({
  selector: 'jhi-product-attribute-set',
  templateUrl: './product-attribute-set.component.html'
})
export class ProductAttributeSetComponent implements OnInit, OnDestroy {
  productAttributeSets: IProductAttributeSet[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productAttributeSetService: ProductAttributeSetService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productAttributeSetService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductAttributeSet[]>) => res.ok),
        map((res: HttpResponse<IProductAttributeSet[]>) => res.body)
      )
      .subscribe((res: IProductAttributeSet[]) => {
        this.productAttributeSets = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductAttributeSets();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductAttributeSet) {
    return item.id;
  }

  registerChangeInProductAttributeSets() {
    this.eventSubscriber = this.eventManager.subscribe('productAttributeSetListModification', response => this.loadAll());
  }
}
