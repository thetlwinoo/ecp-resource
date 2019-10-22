import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IProductChoice } from 'app/shared/model/product-choice.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProductChoiceService } from './product-choice.service';

@Component({
  selector: 'jhi-product-choice',
  templateUrl: './product-choice.component.html'
})
export class ProductChoiceComponent implements OnInit, OnDestroy {
  productChoices: IProductChoice[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected productChoiceService: ProductChoiceService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.productChoiceService
      .query()
      .pipe(
        filter((res: HttpResponse<IProductChoice[]>) => res.ok),
        map((res: HttpResponse<IProductChoice[]>) => res.body)
      )
      .subscribe((res: IProductChoice[]) => {
        this.productChoices = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductChoices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductChoice) {
    return item.id;
  }

  registerChangeInProductChoices() {
    this.eventSubscriber = this.eventManager.subscribe('productChoiceListModification', response => this.loadAll());
  }
}
