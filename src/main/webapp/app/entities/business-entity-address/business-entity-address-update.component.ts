import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBusinessEntityAddress, BusinessEntityAddress } from 'app/shared/model/business-entity-address.model';
import { BusinessEntityAddressService } from './business-entity-address.service';
import { IAddresses } from 'app/shared/model/addresses.model';
import { AddressesService } from 'app/entities/addresses/addresses.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from 'app/entities/address-types/address-types.service';

@Component({
  selector: 'jhi-business-entity-address-update',
  templateUrl: './business-entity-address-update.component.html'
})
export class BusinessEntityAddressUpdateComponent implements OnInit {
  isSaving: boolean;

  addresses: IAddresses[];

  people: IPeople[];

  addresstypes: IAddressTypes[];

  editForm = this.fb.group({
    id: [],
    addressId: [],
    personId: [],
    addressTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected businessEntityAddressService: BusinessEntityAddressService,
    protected addressesService: AddressesService,
    protected peopleService: PeopleService,
    protected addressTypesService: AddressTypesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ businessEntityAddress }) => {
      this.updateForm(businessEntityAddress);
    });
    this.addressesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAddresses[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAddresses[]>) => response.body)
      )
      .subscribe((res: IAddresses[]) => (this.addresses = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.peopleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPeople[]>) => response.body)
      )
      .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.addressTypesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAddressTypes[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAddressTypes[]>) => response.body)
      )
      .subscribe((res: IAddressTypes[]) => (this.addresstypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(businessEntityAddress: IBusinessEntityAddress) {
    this.editForm.patchValue({
      id: businessEntityAddress.id,
      addressId: businessEntityAddress.addressId,
      personId: businessEntityAddress.personId,
      addressTypeId: businessEntityAddress.addressTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const businessEntityAddress = this.createFromForm();
    if (businessEntityAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.businessEntityAddressService.update(businessEntityAddress));
    } else {
      this.subscribeToSaveResponse(this.businessEntityAddressService.create(businessEntityAddress));
    }
  }

  private createFromForm(): IBusinessEntityAddress {
    return {
      ...new BusinessEntityAddress(),
      id: this.editForm.get(['id']).value,
      addressId: this.editForm.get(['addressId']).value,
      personId: this.editForm.get(['personId']).value,
      addressTypeId: this.editForm.get(['addressTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessEntityAddress>>) {
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

  trackAddressesById(index: number, item: IAddresses) {
    return item.id;
  }

  trackPeopleById(index: number, item: IPeople) {
    return item.id;
  }

  trackAddressTypesById(index: number, item: IAddressTypes) {
    return item.id;
  }
}
