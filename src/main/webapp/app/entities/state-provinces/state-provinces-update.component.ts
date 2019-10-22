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
import { IStateProvinces, StateProvinces } from 'app/shared/model/state-provinces.model';
import { StateProvincesService } from './state-provinces.service';
import { ICountries } from 'app/shared/model/countries.model';
import { CountriesService } from 'app/entities/countries/countries.service';

@Component({
  selector: 'jhi-state-provinces-update',
  templateUrl: './state-provinces-update.component.html'
})
export class StateProvincesUpdateComponent implements OnInit {
  isSaving: boolean;

  countries: ICountries[];
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    stateProvinceCode: [null, [Validators.required]],
    stateProvinceName: [null, [Validators.required]],
    salesTerritory: [null, [Validators.required]],
    border: [],
    latestRecordedPopulation: [],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]],
    countryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected stateProvincesService: StateProvincesService,
    protected countriesService: CountriesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ stateProvinces }) => {
      this.updateForm(stateProvinces);
    });
    this.countriesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICountries[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICountries[]>) => response.body)
      )
      .subscribe((res: ICountries[]) => (this.countries = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(stateProvinces: IStateProvinces) {
    this.editForm.patchValue({
      id: stateProvinces.id,
      stateProvinceCode: stateProvinces.stateProvinceCode,
      stateProvinceName: stateProvinces.stateProvinceName,
      salesTerritory: stateProvinces.salesTerritory,
      border: stateProvinces.border,
      latestRecordedPopulation: stateProvinces.latestRecordedPopulation,
      validFrom: stateProvinces.validFrom,
      validTo: stateProvinces.validTo,
      countryId: stateProvinces.countryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const stateProvinces = this.createFromForm();
    if (stateProvinces.id !== undefined) {
      this.subscribeToSaveResponse(this.stateProvincesService.update(stateProvinces));
    } else {
      this.subscribeToSaveResponse(this.stateProvincesService.create(stateProvinces));
    }
  }

  private createFromForm(): IStateProvinces {
    return {
      ...new StateProvinces(),
      id: this.editForm.get(['id']).value,
      stateProvinceCode: this.editForm.get(['stateProvinceCode']).value,
      stateProvinceName: this.editForm.get(['stateProvinceName']).value,
      salesTerritory: this.editForm.get(['salesTerritory']).value,
      border: this.editForm.get(['border']).value,
      latestRecordedPopulation: this.editForm.get(['latestRecordedPopulation']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value,
      countryId: this.editForm.get(['countryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStateProvinces>>) {
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

  trackCountriesById(index: number, item: ICountries) {
    return item.id;
  }
}
