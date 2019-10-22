import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';
import { AccountService } from 'app/core/auth/account.service';
import { WarrantyTypesService } from './warranty-types.service';

@Component({
  selector: 'jhi-warranty-types',
  templateUrl: './warranty-types.component.html'
})
export class WarrantyTypesComponent implements OnInit, OnDestroy {
  warrantyTypes: IWarrantyTypes[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected warrantyTypesService: WarrantyTypesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.warrantyTypesService
      .query()
      .pipe(
        filter((res: HttpResponse<IWarrantyTypes[]>) => res.ok),
        map((res: HttpResponse<IWarrantyTypes[]>) => res.body)
      )
      .subscribe((res: IWarrantyTypes[]) => {
        this.warrantyTypes = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInWarrantyTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IWarrantyTypes) {
    return item.id;
  }

  registerChangeInWarrantyTypes() {
    this.eventSubscriber = this.eventManager.subscribe('warrantyTypesListModification', response => this.loadAll());
  }
}
