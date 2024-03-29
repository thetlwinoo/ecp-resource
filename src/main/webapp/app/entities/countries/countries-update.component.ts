import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ICountries, Countries } from 'app/shared/model/countries.model';
import { CountriesService } from './countries.service';

@Component({
  selector: 'jhi-countries-update',
  templateUrl: './countries-update.component.html'
})
export class CountriesUpdateComponent implements OnInit {
  isSaving: boolean;
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    countryName: [null, [Validators.required]],
    formalName: [null, [Validators.required]],
    isoAplha3Code: [],
    isoNumericCode: [],
    countryType: [],
    latestRecordedPopulation: [],
    continent: [null, [Validators.required]],
    region: [null, [Validators.required]],
    subregion: [null, [Validators.required]],
    border: [],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(protected countriesService: CountriesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ countries }) => {
      this.updateForm(countries);
    });
  }

  updateForm(countries: ICountries) {
    this.editForm.patchValue({
      id: countries.id,
      countryName: countries.countryName,
      formalName: countries.formalName,
      isoAplha3Code: countries.isoAplha3Code,
      isoNumericCode: countries.isoNumericCode,
      countryType: countries.countryType,
      latestRecordedPopulation: countries.latestRecordedPopulation,
      continent: countries.continent,
      region: countries.region,
      subregion: countries.subregion,
      border: countries.border,
      validFrom: countries.validFrom,
      validTo: countries.validTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const countries = this.createFromForm();
    if (countries.id !== undefined) {
      this.subscribeToSaveResponse(this.countriesService.update(countries));
    } else {
      this.subscribeToSaveResponse(this.countriesService.create(countries));
    }
  }

  private createFromForm(): ICountries {
    return {
      ...new Countries(),
      id: this.editForm.get(['id']).value,
      countryName: this.editForm.get(['countryName']).value,
      formalName: this.editForm.get(['formalName']).value,
      isoAplha3Code: this.editForm.get(['isoAplha3Code']).value,
      isoNumericCode: this.editForm.get(['isoNumericCode']).value,
      countryType: this.editForm.get(['countryType']).value,
      latestRecordedPopulation: this.editForm.get(['latestRecordedPopulation']).value,
      continent: this.editForm.get(['continent']).value,
      region: this.editForm.get(['region']).value,
      subregion: this.editForm.get(['subregion']).value,
      border: this.editForm.get(['border']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICountries>>) {
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
