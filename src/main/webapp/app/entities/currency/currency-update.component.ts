import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICurrency, Currency } from 'app/shared/model/currency.model';
import { CurrencyService } from './currency.service';

@Component({
  selector: 'jhi-currency-update',
  templateUrl: './currency-update.component.html'
})
export class CurrencyUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    currencyCode: [null, [Validators.required]],
    currencyName: []
  });

  constructor(protected currencyService: CurrencyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ currency }) => {
      this.updateForm(currency);
    });
  }

  updateForm(currency: ICurrency) {
    this.editForm.patchValue({
      id: currency.id,
      currencyCode: currency.currencyCode,
      currencyName: currency.currencyName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const currency = this.createFromForm();
    if (currency.id !== undefined) {
      this.subscribeToSaveResponse(this.currencyService.update(currency));
    } else {
      this.subscribeToSaveResponse(this.currencyService.create(currency));
    }
  }

  private createFromForm(): ICurrency {
    return {
      ...new Currency(),
      id: this.editForm.get(['id']).value,
      currencyCode: this.editForm.get(['currencyCode']).value,
      currencyName: this.editForm.get(['currencyName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrency>>) {
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
