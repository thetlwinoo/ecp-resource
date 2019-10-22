import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStockItemTransactions, StockItemTransactions } from 'app/shared/model/stock-item-transactions.model';
import { StockItemTransactionsService } from './stock-item-transactions.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from 'app/entities/invoices/invoices.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/suppliers.service';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types/transaction-types.service';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from 'app/entities/purchase-orders/purchase-orders.service';

@Component({
  selector: 'jhi-stock-item-transactions-update',
  templateUrl: './stock-item-transactions-update.component.html'
})
export class StockItemTransactionsUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitems: IStockItems[];

  customers: ICustomers[];

  invoices: IInvoices[];

  suppliers: ISuppliers[];

  transactiontypes: ITransactionTypes[];

  purchaseorders: IPurchaseOrders[];
  transactionOccurredWhenDp: any;
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    transactionOccurredWhen: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
    lastEditedBy: [],
    lastEditedWhen: [],
    stockItemId: [],
    customerId: [],
    invoiceId: [],
    supplierId: [],
    transactionTypeId: [],
    purchaseOrderId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stockItemTransactionsService: StockItemTransactionsService,
    protected stockItemsService: StockItemsService,
    protected customersService: CustomersService,
    protected invoicesService: InvoicesService,
    protected suppliersService: SuppliersService,
    protected transactionTypesService: TransactionTypesService,
    protected purchaseOrdersService: PurchaseOrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stockItemTransactions }) => {
      this.updateForm(stockItemTransactions);
    });
    this.stockItemsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStockItems[]>) => response.body)
      )
      .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.customersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomers[]>) => response.body)
      )
      .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.invoicesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInvoices[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInvoices[]>) => response.body)
      )
      .subscribe((res: IInvoices[]) => (this.invoices = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.suppliersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISuppliers[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISuppliers[]>) => response.body)
      )
      .subscribe((res: ISuppliers[]) => (this.suppliers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.transactionTypesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransactionTypes[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransactionTypes[]>) => response.body)
      )
      .subscribe((res: ITransactionTypes[]) => (this.transactiontypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.purchaseOrdersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPurchaseOrders[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPurchaseOrders[]>) => response.body)
      )
      .subscribe((res: IPurchaseOrders[]) => (this.purchaseorders = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(stockItemTransactions: IStockItemTransactions) {
    this.editForm.patchValue({
      id: stockItemTransactions.id,
      transactionOccurredWhen: stockItemTransactions.transactionOccurredWhen,
      quantity: stockItemTransactions.quantity,
      lastEditedBy: stockItemTransactions.lastEditedBy,
      lastEditedWhen: stockItemTransactions.lastEditedWhen,
      stockItemId: stockItemTransactions.stockItemId,
      customerId: stockItemTransactions.customerId,
      invoiceId: stockItemTransactions.invoiceId,
      supplierId: stockItemTransactions.supplierId,
      transactionTypeId: stockItemTransactions.transactionTypeId,
      purchaseOrderId: stockItemTransactions.purchaseOrderId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stockItemTransactions = this.createFromForm();
    if (stockItemTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.stockItemTransactionsService.update(stockItemTransactions));
    } else {
      this.subscribeToSaveResponse(this.stockItemTransactionsService.create(stockItemTransactions));
    }
  }

  private createFromForm(): IStockItemTransactions {
    return {
      ...new StockItemTransactions(),
      id: this.editForm.get(['id']).value,
      transactionOccurredWhen: this.editForm.get(['transactionOccurredWhen']).value,
      quantity: this.editForm.get(['quantity']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      stockItemId: this.editForm.get(['stockItemId']).value,
      customerId: this.editForm.get(['customerId']).value,
      invoiceId: this.editForm.get(['invoiceId']).value,
      supplierId: this.editForm.get(['supplierId']).value,
      transactionTypeId: this.editForm.get(['transactionTypeId']).value,
      purchaseOrderId: this.editForm.get(['purchaseOrderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItemTransactions>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }

  trackInvoicesById(index: number, item: IInvoices) {
    return item.id;
  }

  trackSuppliersById(index: number, item: ISuppliers) {
    return item.id;
  }

  trackTransactionTypesById(index: number, item: ITransactionTypes) {
    return item.id;
  }

  trackPurchaseOrdersById(index: number, item: IPurchaseOrders) {
    return item.id;
  }
}
