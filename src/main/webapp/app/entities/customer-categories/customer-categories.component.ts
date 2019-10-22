import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerCategories } from 'app/shared/model/customer-categories.model';
import { AccountService } from 'app/core/auth/account.service';
import { CustomerCategoriesService } from './customer-categories.service';

@Component({
  selector: 'jhi-customer-categories',
  templateUrl: './customer-categories.component.html'
})
export class CustomerCategoriesComponent implements OnInit, OnDestroy {
  customerCategories: ICustomerCategories[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected customerCategoriesService: CustomerCategoriesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.customerCategoriesService
      .query()
      .pipe(
        filter((res: HttpResponse<ICustomerCategories[]>) => res.ok),
        map((res: HttpResponse<ICustomerCategories[]>) => res.body)
      )
      .subscribe((res: ICustomerCategories[]) => {
        this.customerCategories = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCustomerCategories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomerCategories) {
    return item.id;
  }

  registerChangeInCustomerCategories() {
    this.eventSubscriber = this.eventManager.subscribe('customerCategoriesListModification', response => this.loadAll());
  }
}
