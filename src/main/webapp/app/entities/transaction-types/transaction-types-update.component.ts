import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ITransactionTypes, TransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from './transaction-types.service';

@Component({
  selector: 'jhi-transaction-types-update',
  templateUrl: './transaction-types-update.component.html'
})
export class TransactionTypesUpdateComponent implements OnInit {
  isSaving: boolean;
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    transactionTypeName: [null, [Validators.required]],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(
    protected transactionTypesService: TransactionTypesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionTypes }) => {
      this.updateForm(transactionTypes);
    });
  }

  updateForm(transactionTypes: ITransactionTypes) {
    this.editForm.patchValue({
      id: transactionTypes.id,
      transactionTypeName: transactionTypes.transactionTypeName,
      validFrom: transactionTypes.validFrom,
      validTo: transactionTypes.validTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionTypes = this.createFromForm();
    if (transactionTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionTypesService.update(transactionTypes));
    } else {
      this.subscribeToSaveResponse(this.transactionTypesService.create(transactionTypes));
    }
  }

  private createFromForm(): ITransactionTypes {
    return {
      ...new TransactionTypes(),
      id: this.editForm.get(['id']).value,
      transactionTypeName: this.editForm.get(['transactionTypeName']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionTypes>>) {
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
