import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { AccountService } from 'app/core/auth/account.service';
import { SupplierImportedDocumentService } from './supplier-imported-document.service';

@Component({
  selector: 'jhi-supplier-imported-document',
  templateUrl: './supplier-imported-document.component.html'
})
export class SupplierImportedDocumentComponent implements OnInit, OnDestroy {
  supplierImportedDocuments: ISupplierImportedDocument[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected supplierImportedDocumentService: SupplierImportedDocumentService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.supplierImportedDocumentService
      .query()
      .pipe(
        filter((res: HttpResponse<ISupplierImportedDocument[]>) => res.ok),
        map((res: HttpResponse<ISupplierImportedDocument[]>) => res.body)
      )
      .subscribe((res: ISupplierImportedDocument[]) => {
        this.supplierImportedDocuments = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSupplierImportedDocuments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISupplierImportedDocument) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInSupplierImportedDocuments() {
    this.eventSubscriber = this.eventManager.subscribe('supplierImportedDocumentListModification', response => this.loadAll());
  }
}
