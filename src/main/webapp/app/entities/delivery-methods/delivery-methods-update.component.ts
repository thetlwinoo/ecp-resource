import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IDeliveryMethods, DeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from './delivery-methods.service';

@Component({
  selector: 'jhi-delivery-methods-update',
  templateUrl: './delivery-methods-update.component.html'
})
export class DeliveryMethodsUpdateComponent implements OnInit {
  isSaving: boolean;
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    deliveryMethodName: [null, [Validators.required]],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(
    protected deliveryMethodsService: DeliveryMethodsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ deliveryMethods }) => {
      this.updateForm(deliveryMethods);
    });
  }

  updateForm(deliveryMethods: IDeliveryMethods) {
    this.editForm.patchValue({
      id: deliveryMethods.id,
      deliveryMethodName: deliveryMethods.deliveryMethodName,
      validFrom: deliveryMethods.validFrom,
      validTo: deliveryMethods.validTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const deliveryMethods = this.createFromForm();
    if (deliveryMethods.id !== undefined) {
      this.subscribeToSaveResponse(this.deliveryMethodsService.update(deliveryMethods));
    } else {
      this.subscribeToSaveResponse(this.deliveryMethodsService.create(deliveryMethods));
    }
  }

  private createFromForm(): IDeliveryMethods {
    return {
      ...new DeliveryMethods(),
      id: this.editForm.get(['id']).value,
      deliveryMethodName: this.editForm.get(['deliveryMethodName']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveryMethods>>) {
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
