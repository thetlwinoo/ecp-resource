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
import { IOrders, Orders } from 'app/shared/model/orders.model';
import { OrdersService } from './orders.service';
import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from 'app/entities/reviews/reviews.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IAddresses } from 'app/shared/model/addresses.model';
import { AddressesService } from 'app/entities/addresses/addresses.service';
import { IShipMethod } from 'app/shared/model/ship-method.model';
import { ShipMethodService } from 'app/entities/ship-method/ship-method.service';
import { ICurrencyRate } from 'app/shared/model/currency-rate.model';
import { CurrencyRateService } from 'app/entities/currency-rate/currency-rate.service';
import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from 'app/entities/payment-transactions/payment-transactions.service';
import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from 'app/entities/special-deals/special-deals.service';

@Component({
  selector: 'jhi-orders-update',
  templateUrl: './orders-update.component.html'
})
export class OrdersUpdateComponent implements OnInit {
  isSaving: boolean;

  orderonreviews: IReviews[];

  customers: ICustomers[];

  addresses: IAddresses[];

  shipmethods: IShipMethod[];

  currencyrates: ICurrencyRate[];

  paymenttransactions: IPaymentTransactions[];

  specialdeals: ISpecialDeals[];
  orderDateDp: any;
  dueDateDp: any;
  shipDateDp: any;
  pickingCompletedWhenDp: any;
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    orderDate: [null, [Validators.required]],
    dueDate: [],
    shipDate: [],
    paymentStatus: [],
    orderFlag: [],
    orderNumber: [],
    subTotal: [],
    taxAmount: [],
    frieight: [],
    totalDue: [],
    comments: [],
    deliveryInstructions: [],
    internalComments: [],
    pickingCompletedWhen: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    orderOnReviewId: [],
    customerId: [],
    shipToAddressId: [],
    billToAddressId: [],
    shipMethodId: [],
    currencyRateId: [],
    specialDealsId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ordersService: OrdersService,
    protected reviewsService: ReviewsService,
    protected customersService: CustomersService,
    protected addressesService: AddressesService,
    protected shipMethodService: ShipMethodService,
    protected currencyRateService: CurrencyRateService,
    protected paymentTransactionsService: PaymentTransactionsService,
    protected specialDealsService: SpecialDealsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ orders }) => {
      this.updateForm(orders);
    });
    this.reviewsService
      .query({ 'orderId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IReviews[]>) => mayBeOk.ok),
        map((response: HttpResponse<IReviews[]>) => response.body)
      )
      .subscribe(
        (res: IReviews[]) => {
          if (!this.editForm.get('orderOnReviewId').value) {
            this.orderonreviews = res;
          } else {
            this.reviewsService
              .find(this.editForm.get('orderOnReviewId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IReviews>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IReviews>) => subResponse.body)
              )
              .subscribe(
                (subRes: IReviews) => (this.orderonreviews = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.customersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomers[]>) => response.body)
      )
      .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.addressesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAddresses[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAddresses[]>) => response.body)
      )
      .subscribe((res: IAddresses[]) => (this.addresses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.shipMethodService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IShipMethod[]>) => mayBeOk.ok),
        map((response: HttpResponse<IShipMethod[]>) => response.body)
      )
      .subscribe((res: IShipMethod[]) => (this.shipmethods = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.currencyRateService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICurrencyRate[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICurrencyRate[]>) => response.body)
      )
      .subscribe((res: ICurrencyRate[]) => (this.currencyrates = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.paymentTransactionsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPaymentTransactions[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPaymentTransactions[]>) => response.body)
      )
      .subscribe((res: IPaymentTransactions[]) => (this.paymenttransactions = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.specialDealsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISpecialDeals[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISpecialDeals[]>) => response.body)
      )
      .subscribe((res: ISpecialDeals[]) => (this.specialdeals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(orders: IOrders) {
    this.editForm.patchValue({
      id: orders.id,
      orderDate: orders.orderDate,
      dueDate: orders.dueDate,
      shipDate: orders.shipDate,
      paymentStatus: orders.paymentStatus,
      orderFlag: orders.orderFlag,
      orderNumber: orders.orderNumber,
      subTotal: orders.subTotal,
      taxAmount: orders.taxAmount,
      frieight: orders.frieight,
      totalDue: orders.totalDue,
      comments: orders.comments,
      deliveryInstructions: orders.deliveryInstructions,
      internalComments: orders.internalComments,
      pickingCompletedWhen: orders.pickingCompletedWhen,
      lastEditedBy: orders.lastEditedBy,
      lastEditedWhen: orders.lastEditedWhen,
      orderOnReviewId: orders.orderOnReviewId,
      customerId: orders.customerId,
      shipToAddressId: orders.shipToAddressId,
      billToAddressId: orders.billToAddressId,
      shipMethodId: orders.shipMethodId,
      currencyRateId: orders.currencyRateId,
      specialDealsId: orders.specialDealsId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const orders = this.createFromForm();
    if (orders.id !== undefined) {
      this.subscribeToSaveResponse(this.ordersService.update(orders));
    } else {
      this.subscribeToSaveResponse(this.ordersService.create(orders));
    }
  }

  private createFromForm(): IOrders {
    return {
      ...new Orders(),
      id: this.editForm.get(['id']).value,
      orderDate: this.editForm.get(['orderDate']).value,
      dueDate: this.editForm.get(['dueDate']).value,
      shipDate: this.editForm.get(['shipDate']).value,
      paymentStatus: this.editForm.get(['paymentStatus']).value,
      orderFlag: this.editForm.get(['orderFlag']).value,
      orderNumber: this.editForm.get(['orderNumber']).value,
      subTotal: this.editForm.get(['subTotal']).value,
      taxAmount: this.editForm.get(['taxAmount']).value,
      frieight: this.editForm.get(['frieight']).value,
      totalDue: this.editForm.get(['totalDue']).value,
      comments: this.editForm.get(['comments']).value,
      deliveryInstructions: this.editForm.get(['deliveryInstructions']).value,
      internalComments: this.editForm.get(['internalComments']).value,
      pickingCompletedWhen: this.editForm.get(['pickingCompletedWhen']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      orderOnReviewId: this.editForm.get(['orderOnReviewId']).value,
      customerId: this.editForm.get(['customerId']).value,
      shipToAddressId: this.editForm.get(['shipToAddressId']).value,
      billToAddressId: this.editForm.get(['billToAddressId']).value,
      shipMethodId: this.editForm.get(['shipMethodId']).value,
      currencyRateId: this.editForm.get(['currencyRateId']).value,
      specialDealsId: this.editForm.get(['specialDealsId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrders>>) {
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

  trackReviewsById(index: number, item: IReviews) {
    return item.id;
  }

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }

  trackAddressesById(index: number, item: IAddresses) {
    return item.id;
  }

  trackShipMethodById(index: number, item: IShipMethod) {
    return item.id;
  }

  trackCurrencyRateById(index: number, item: ICurrencyRate) {
    return item.id;
  }

  trackPaymentTransactionsById(index: number, item: IPaymentTransactions) {
    return item.id;
  }

  trackSpecialDealsById(index: number, item: ISpecialDeals) {
    return item.id;
  }
}
