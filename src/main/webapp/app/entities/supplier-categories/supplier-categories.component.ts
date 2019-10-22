import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';
import { AccountService } from 'app/core/auth/account.service';
import { SupplierCategoriesService } from './supplier-categories.service';

@Component({
  selector: 'jhi-supplier-categories',
  templateUrl: './supplier-categories.component.html'
})
export class SupplierCategoriesComponent implements OnInit, OnDestroy {
  supplierCategories: ISupplierCategories[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected supplierCategoriesService: SupplierCategoriesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.supplierCategoriesService
      .query()
      .pipe(
        filter((res: HttpResponse<ISupplierCategories[]>) => res.ok),
        map((res: HttpResponse<ISupplierCategories[]>) => res.body)
      )
      .subscribe((res: ISupplierCategories[]) => {
        this.supplierCategories = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSupplierCategories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISupplierCategories) {
    return item.id;
  }

  registerChangeInSupplierCategories() {
    this.eventSubscriber = this.eventManager.subscribe('supplierCategoriesListModification', response => this.loadAll());
  }
}
