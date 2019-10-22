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
import { ICustomerTransactions, CustomerTransactions } from 'app/shared/model/customer-transactions.model';
import { CustomerTransactionsService } from './customer-transactions.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IPaymentMethods } from 'app/shared/model/payment-methods.model';
import { PaymentMethodsService } from 'app/entities/payment-methods/payment-methods.service';
import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from 'app/entities/payment-transactions/payment-transactions.service';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from 'app/entities/transaction-types/transaction-types.service';
import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from 'app/entities/invoices/invoices.service';

@Component({
  selector: 'jhi-customer-transactions-update',
  templateUrl: './customer-transactions-update.component.html'
})
export class CustomerTransactionsUpdateComponent implements OnInit {
  isSaving: boolean;

  customers: ICustomers[];

  paymentmethods: IPaymentMethods[];

  paymenttransactions: IPaymentTransactions[];

  transactiontypes: ITransactionTypes[];

  invoices: IInvoices[];
  transactionDateDp: any;
  finalizationDateDp: any;
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    transactionDate: [null, [Validators.required]],
    amountExcludingTax: [null, [Validators.required]],
    taxAmount: [null, [Validators.required]],
    transactionAmount: [null, [Validators.required]],
    outstandingBalance: [null, [Validators.required]],
    finalizationDate: [],
    isFinalized: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    customerId: [],
    paymentMethodId: [],
    paymentTransactionId: [],
    transactionTypeId: [],
    invoiceId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected customerTransactionsService: CustomerTransactionsService,
    protected customersService: CustomersService,
    protected paymentMethodsService: PaymentMethodsService,
    protected paymentTransactionsService: PaymentTransactionsService,
    protected transactionTypesService: TransactionTypesService,
    protected invoicesService: InvoicesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ customerTransactions }) => {
      this.updateForm(customerTransactions);
    });
    this.customersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomers[]>) => response.body)
      )
      .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.paymentMethodsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPaymentMethods[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPaymentMethods[]>) => response.body)
      )
      .subscribe((res: IPaymentMethods[]) => (this.paymentmethods = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.paymentTransactionsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPaymentTransactions[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPaymentTransactions[]>) => response.body)
      )
      .subscribe((res: IPaymentTransactions[]) => (this.paymenttransactions = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.transactionTypesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransactionTypes[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransactionTypes[]>) => response.body)
      )
      .subscribe((res: ITransactionTypes[]) => (this.transactiontypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.invoicesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInvoices[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInvoices[]>) => response.body)
      )
      .subscribe((res: IInvoices[]) => (this.invoices = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(customerTransactions: ICustomerTransactions) {
    this.editForm.patchValue({
      id: customerTransactions.id,
      transactionDate: customerTransactions.transactionDate,
      amountExcludingTax: customerTransactions.amountExcludingTax,
      taxAmount: customerTransactions.taxAmount,
      transactionAmount: customerTransactions.transactionAmount,
      outstandingBalance: customerTransactions.outstandingBalance,
      finalizationDate: customerTransactions.finalizationDate,
      isFinalized: customerTransactions.isFinalized,
      lastEditedBy: customerTransactions.lastEditedBy,
      lastEditedWhen: customerTransactions.lastEditedWhen,
      customerId: customerTransactions.customerId,
      paymentMethodId: customerTransactions.paymentMethodId,
      paymentTransactionId: customerTransactions.paymentTransactionId,
      transactionTypeId: customerTransactions.transactionTypeId,
      invoiceId: customerTransactions.invoiceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const customerTransactions = this.createFromForm();
    if (customerTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.customerTransactionsService.update(customerTransactions));
    } else {
      this.subscribeToSaveResponse(this.customerTransactionsService.create(customerTransactions));
    }
  }

  private createFromForm(): ICustomerTransactions {
    return {
      ...new CustomerTransactions(),
      id: this.editForm.get(['id']).value,
      transactionDate: this.editForm.get(['transactionDate']).value,
      amountExcludingTax: this.editForm.get(['amountExcludingTax']).value,
      taxAmount: this.editForm.get(['taxAmount']).value,
      transactionAmount: this.editForm.get(['transactionAmount']).value,
      outstandingBalance: this.editForm.get(['outstandingBalance']).value,
      finalizationDate: this.editForm.get(['finalizationDate']).value,
      isFinalized: this.editForm.get(['isFinalized']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      customerId: this.editForm.get(['customerId']).value,
      paymentMethodId: this.editForm.get(['paymentMethodId']).value,
      paymentTransactionId: this.editForm.get(['paymentTransactionId']).value,
      transactionTypeId: this.editForm.get(['transactionTypeId']).value,
      invoiceId: this.editForm.get(['invoiceId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustomerTransactions>>) {
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

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }

  trackPaymentMethodsById(index: number, item: IPaymentMethods) {
    return item.id;
  }

  trackPaymentTransactionsById(index: number, item: IPaymentTransactions) {
    return item.id;
  }

  trackTransactionTypesById(index: number, item: ITransactionTypes) {
    return item.id;
  }

  trackInvoicesById(index: number, item: IInvoices) {
    return item.id;
  }
}
