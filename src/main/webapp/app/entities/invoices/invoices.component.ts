import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoices } from 'app/shared/model/invoices.model';
import { AccountService } from 'app/core/auth/account.service';
import { InvoicesService } from './invoices.service';

@Component({
  selector: 'jhi-invoices',
  templateUrl: './invoices.component.html'
})
export class InvoicesComponent implements OnInit, OnDestroy {
  invoices: IInvoices[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected invoicesService: InvoicesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.invoicesService
      .query()
      .pipe(
        filter((res: HttpResponse<IInvoices[]>) => res.ok),
        map((res: HttpResponse<IInvoices[]>) => res.body)
      )
      .subscribe((res: IInvoices[]) => {
        this.invoices = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInvoices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInvoices) {
    return item.id;
  }

  registerChangeInInvoices() {
    this.eventSubscriber = this.eventManager.subscribe('invoicesListModification', response => this.loadAll());
  }
}
