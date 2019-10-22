import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPaymentTransactions, PaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from './payment-transactions.service';
import { IOrders } from 'app/shared/model/orders.model';
import { OrdersService } from 'app/entities/orders/orders.service';

@Component({
  selector: 'jhi-payment-transactions-update',
  templateUrl: './payment-transactions-update.component.html'
})
export class PaymentTransactionsUpdateComponent implements OnInit {
  isSaving: boolean;

  paymentonorders: IOrders[];
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    returnedCompletedPaymentData: [null, [Validators.required]],
    lastEditedBy: [],
    lastEditedWhen: [],
    paymentOnOrderId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected paymentTransactionsService: PaymentTransactionsService,
    protected ordersService: OrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paymentTransactions }) => {
      this.updateForm(paymentTransactions);
    });
    this.ordersService
      .query({ 'paymentTransactionId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IOrders[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOrders[]>) => response.body)
      )
      .subscribe(
        (res: IOrders[]) => {
          if (!this.editForm.get('paymentOnOrderId').value) {
            this.paymentonorders = res;
          } else {
            this.ordersService
              .find(this.editForm.get('paymentOnOrderId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IOrders>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IOrders>) => subResponse.body)
              )
              .subscribe(
                (subRes: IOrders) => (this.paymentonorders = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(paymentTransactions: IPaymentTransactions) {
    this.editForm.patchValue({
      id: paymentTransactions.id,
      returnedCompletedPaymentData: paymentTransactions.returnedCompletedPaymentData,
      lastEditedBy: paymentTransactions.lastEditedBy,
      lastEditedWhen: paymentTransactions.lastEditedWhen,
      paymentOnOrderId: paymentTransactions.paymentOnOrderId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paymentTransactions = this.createFromForm();
    if (paymentTransactions.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentTransactionsService.update(paymentTransactions));
    } else {
      this.subscribeToSaveResponse(this.paymentTransactionsService.create(paymentTransactions));
    }
  }

  private createFromForm(): IPaymentTransactions {
    return {
      ...new PaymentTransactions(),
      id: this.editForm.get(['id']).value,
      returnedCompletedPaymentData: this.editForm.get(['returnedCompletedPaymentData']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      paymentOnOrderId: this.editForm.get(['paymentOnOrderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentTransactions>>) {
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

  trackOrdersById(index: number, item: IOrders) {
    return item.id;
  }
}
