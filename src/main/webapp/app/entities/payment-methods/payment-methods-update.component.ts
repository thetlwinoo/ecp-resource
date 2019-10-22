import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPaymentMethods, PaymentMethods } from 'app/shared/model/payment-methods.model';
import { PaymentMethodsService } from './payment-methods.service';

@Component({
  selector: 'jhi-payment-methods-update',
  templateUrl: './payment-methods-update.component.html'
})
export class PaymentMethodsUpdateComponent implements OnInit {
  isSaving: boolean;
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    paymentMethodName: [null, [Validators.required]],
    activeInd: [null, [Validators.required]],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(protected paymentMethodsService: PaymentMethodsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paymentMethods }) => {
      this.updateForm(paymentMethods);
    });
  }

  updateForm(paymentMethods: IPaymentMethods) {
    this.editForm.patchValue({
      id: paymentMethods.id,
      paymentMethodName: paymentMethods.paymentMethodName,
      activeInd: paymentMethods.activeInd,
      validFrom: paymentMethods.validFrom,
      validTo: paymentMethods.validTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paymentMethods = this.createFromForm();
    if (paymentMethods.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentMethodsService.update(paymentMethods));
    } else {
      this.subscribeToSaveResponse(this.paymentMethodsService.create(paymentMethods));
    }
  }

  private createFromForm(): IPaymentMethods {
    return {
      ...new PaymentMethods(),
      id: this.editForm.get(['id']).value,
      paymentMethodName: this.editForm.get(['paymentMethodName']).value,
      activeInd: this.editForm.get(['activeInd']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentMethods>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
