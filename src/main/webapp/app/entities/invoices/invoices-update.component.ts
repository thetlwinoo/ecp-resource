import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IInvoices, Invoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from './invoices.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from 'app/entities/delivery-methods/delivery-methods.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders/orders.service';

@Component({
  selector: 'jhi-invoices-update',
  templateUrl: './invoices-update.component.html'
})
export class InvoicesUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPeople[];

  customers: ICustomers[];

  deliverymethods: IDeliveryMethods[];

  orders: IOrders[];
  invoiceDateDp: any;
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    invoiceDate: [null, [Validators.required]],
    customerPurchaseOrderNumber: [],
    isCreditNote: [null, [Validators.required]],
    creditNoteReason: [],
    comments: [],
    deliveryInstructions: [],
    internalComments: [],
    totalDryItems: [null, [Validators.required]],
    totalChillerItems: [null, [Validators.required]],
    deliveryRun: [],
    runPosition: [],
    returnedDeliveryData: [],
    confirmedDeliveryTime: [],
    confirmedReceivedBy: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    contactPersonId: [],
    salespersonPersonId: [],
    packedByPersonId: [],
    accountsPersonId: [],
    customerId: [],
    billToCustomerId: [],
    deliveryMethodId: [],
    orderId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected invoicesService: InvoicesService,
    protected peopleService: PeopleService,
    protected customersService: CustomersService,
    protected deliveryMethodsService: DeliveryMethodsService,
    protected ordersService: OrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invoices }) => {
      this.updateForm(invoices);
    });
    this.peopleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPeople[]>) => response.body)
      )
      .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.customersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomers[]>) => response.body)
      )
      .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.deliveryMethodsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDeliveryMethods[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDeliveryMethods[]>) => response.body)
      )
      .subscribe((res: IDeliveryMethods[]) => (this.deliverymethods = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ordersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOrders[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrders[]>) => response.body)
      )
      .subscribe((res: IOrders[]) => (this.orders = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(invoices: IInvoices) {
    this.editForm.patchValue({
      id: invoices.id,
      invoiceDate: invoices.invoiceDate,
      customerPurchaseOrderNumber: invoices.customerPurchaseOrderNumber,
      isCreditNote: invoices.isCreditNote,
      creditNoteReason: invoices.creditNoteReason,
      comments: invoices.comments,
      deliveryInstructions: invoices.deliveryInstructions,
      internalComments: invoices.internalComments,
      totalDryItems: invoices.totalDryItems,
      totalChillerItems: invoices.totalChillerItems,
      deliveryRun: invoices.deliveryRun,
      runPosition: invoices.runPosition,
      returnedDeliveryData: invoices.returnedDeliveryData,
      confirmedDeliveryTime: invoices.confirmedDeliveryTime != null ? invoices.confirmedDeliveryTime.format(DATE_TIME_FORMAT) : null,
      confirmedReceivedBy: invoices.confirmedReceivedBy,
      lastEditedBy: invoices.lastEditedBy,
      lastEditedWhen: invoices.lastEditedWhen,
      contactPersonId: invoices.contactPersonId,
      salespersonPersonId: invoices.salespersonPersonId,
      packedByPersonId: invoices.packedByPersonId,
      accountsPersonId: invoices.accountsPersonId,
      customerId: invoices.customerId,
      billToCustomerId: invoices.billToCustomerId,
      deliveryMethodId: invoices.deliveryMethodId,
      orderId: invoices.orderId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const invoices = this.createFromForm();
    if (invoices.id !== undefined) {
      this.subscribeToSaveResponse(this.invoicesService.update(invoices));
    } else {
      this.subscribeToSaveResponse(this.invoicesService.create(invoices));
    }
  }

  private createFromForm(): IInvoices {
    return {
      ...new Invoices(),
      id: this.editForm.get(['id']).value,
      invoiceDate: this.editForm.get(['invoiceDate']).value,
      customerPurchaseOrderNumber: this.editForm.get(['customerPurchaseOrderNumber']).value,
      isCreditNote: this.editForm.get(['isCreditNote']).value,
      creditNoteReason: this.editForm.get(['creditNoteReason']).value,
      comments: this.editForm.get(['comments']).value,
      deliveryInstructions: this.editForm.get(['deliveryInstructions']).value,
      internalComments: this.editForm.get(['internalComments']).value,
      totalDryItems: this.editForm.get(['totalDryItems']).value,
      totalChillerItems: this.editForm.get(['totalChillerItems']).value,
      deliveryRun: this.editForm.get(['deliveryRun']).value,
      runPosition: this.editForm.get(['runPosition']).value,
      returnedDeliveryData: this.editForm.get(['returnedDeliveryData']).value,
      confirmedDeliveryTime:
        this.editForm.get(['confirmedDeliveryTime']).value != null
          ? moment(this.editForm.get(['confirmedDeliveryTime']).value, DATE_TIME_FORMAT)
          : undefined,
      confirmedReceivedBy: this.editForm.get(['confirmedReceivedBy']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      contactPersonId: this.editForm.get(['contactPersonId']).value,
      salespersonPersonId: this.editForm.get(['salespersonPersonId']).value,
      packedByPersonId: this.editForm.get(['packedByPersonId']).value,
      accountsPersonId: this.editForm.get(['accountsPersonId']).value,
      customerId: this.editForm.get(['customerId']).value,
      billToCustomerId: this.editForm.get(['billToCustomerId']).value,
      deliveryMethodId: this.editForm.get(['deliveryMethodId']).value,
      orderId: this.editForm.get(['orderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvoices>>) {
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

  trackPeopleById(index: number, item: IPeople) {
    return item.id;
  }

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }

  trackDeliveryMethodsById(index: number, item: IDeliveryMethods) {
    return item.id;
  }

  trackOrdersById(index: number, item: IOrders) {
    return item.id;
  }
}
