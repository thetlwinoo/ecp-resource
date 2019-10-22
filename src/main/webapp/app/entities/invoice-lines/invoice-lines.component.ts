import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoiceLines } from 'app/shared/model/invoice-lines.model';
import { AccountService } from 'app/core/auth/account.service';
import { InvoiceLinesService } from './invoice-lines.service';

@Component({
  selector: 'jhi-invoice-lines',
  templateUrl: './invoice-lines.component.html'
})
export class InvoiceLinesComponent implements OnInit, OnDestroy {
  invoiceLines: IInvoiceLines[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected invoiceLinesService: InvoiceLinesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.invoiceLinesService
      .query()
      .pipe(
        filter((res: HttpResponse<IInvoiceLines[]>) => res.ok),
        map((res: HttpResponse<IInvoiceLines[]>) => res.body)
      )
      .subscribe((res: IInvoiceLines[]) => {
        this.invoiceLines = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInvoiceLines();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInvoiceLines) {
    return item.id;
  }

  registerChangeInInvoiceLines() {
    this.eventSubscriber = this.eventManager.subscribe('invoiceLinesListModification', response => this.loadAll());
  }
}
