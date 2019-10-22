import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISuppliers, Suppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from './suppliers.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';
import { SupplierCategoriesService } from 'app/entities/supplier-categories/supplier-categories.service';
import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from 'app/entities/delivery-methods/delivery-methods.service';
import { ICities } from 'app/shared/model/cities.model';
import { CitiesService } from 'app/entities/cities/cities.service';

@Component({
  selector: 'jhi-suppliers-update',
  templateUrl: './suppliers-update.component.html'
})
export class SuppliersUpdateComponent implements OnInit {
  isSaving: boolean;

  people: IPeople[];

  suppliercategories: ISupplierCategories[];

  deliverymethods: IDeliveryMethods[];

  cities: ICities[];
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    supplierName: [null, [Validators.required]],
    supplierReference: [],
    bankAccountName: [],
    bankAccountBranch: [],
    bankAccountCode: [],
    bankAccountNumber: [],
    bankInternationalCode: [],
    paymentDays: [null, [Validators.required]],
    internalComments: [],
    phoneNumber: [null, [Validators.required]],
    faxNumber: [],
    websiteURL: [],
    webServiceUrl: [],
    creditRating: [],
    activeFlag: [],
    avatar: [],
    avatarContentType: [],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]],
    primaryContactPersonId: [],
    alternateContactPersonId: [],
    supplierCategoryId: [],
    deliveryMethodId: [],
    deliveryCityId: [],
    postalCityId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected suppliersService: SuppliersService,
    protected peopleService: PeopleService,
    protected supplierCategoriesService: SupplierCategoriesService,
    protected deliveryMethodsService: DeliveryMethodsService,
    protected citiesService: CitiesService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ suppliers }) => {
      this.updateForm(suppliers);
    });
    this.peopleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPeople[]>) => response.body)
      )
      .subscribe((res: IPeople[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.supplierCategoriesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISupplierCategories[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISupplierCategories[]>) => response.body)
      )
      .subscribe((res: ISupplierCategories[]) => (this.suppliercategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.deliveryMethodsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDeliveryMethods[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDeliveryMethods[]>) => response.body)
      )
      .subscribe((res: IDeliveryMethods[]) => (this.deliverymethods = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.citiesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICities[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICities[]>) => response.body)
      )
      .subscribe((res: ICities[]) => (this.cities = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(suppliers: ISuppliers) {
    this.editForm.patchValue({
      id: suppliers.id,
      supplierName: suppliers.supplierName,
      supplierReference: suppliers.supplierReference,
      bankAccountName: suppliers.bankAccountName,
      bankAccountBranch: suppliers.bankAccountBranch,
      bankAccountCode: suppliers.bankAccountCode,
      bankAccountNumber: suppliers.bankAccountNumber,
      bankInternationalCode: suppliers.bankInternationalCode,
      paymentDays: suppliers.paymentDays,
      internalComments: suppliers.internalComments,
      phoneNumber: suppliers.phoneNumber,
      faxNumber: suppliers.faxNumber,
      websiteURL: suppliers.websiteURL,
      webServiceUrl: suppliers.webServiceUrl,
      creditRating: suppliers.creditRating,
      activeFlag: suppliers.activeFlag,
      avatar: suppliers.avatar,
      avatarContentType: suppliers.avatarContentType,
      validFrom: suppliers.validFrom,
      validTo: suppliers.validTo,
      primaryContactPersonId: suppliers.primaryContactPersonId,
      alternateContactPersonId: suppliers.alternateContactPersonId,
      supplierCategoryId: suppliers.supplierCategoryId,
      deliveryMethodId: suppliers.deliveryMethodId,
      deliveryCityId: suppliers.deliveryCityId,
      postalCityId: suppliers.postalCityId
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const suppliers = this.createFromForm();
    if (suppliers.id !== undefined) {
      this.subscribeToSaveResponse(this.suppliersService.update(suppliers));
    } else {
      this.subscribeToSaveResponse(this.suppliersService.create(suppliers));
    }
  }

  private createFromForm(): ISuppliers {
    return {
      ...new Suppliers(),
      id: this.editForm.get(['id']).value,
      supplierName: this.editForm.get(['supplierName']).value,
      supplierReference: this.editForm.get(['supplierReference']).value,
      bankAccountName: this.editForm.get(['bankAccountName']).value,
      bankAccountBranch: this.editForm.get(['bankAccountBranch']).value,
      bankAccountCode: this.editForm.get(['bankAccountCode']).value,
      bankAccountNumber: this.editForm.get(['bankAccountNumber']).value,
      bankInternationalCode: this.editForm.get(['bankInternationalCode']).value,
      paymentDays: this.editForm.get(['paymentDays']).value,
      internalComments: this.editForm.get(['internalComments']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      faxNumber: this.editForm.get(['faxNumber']).value,
      websiteURL: this.editForm.get(['websiteURL']).value,
      webServiceUrl: this.editForm.get(['webServiceUrl']).value,
      creditRating: this.editForm.get(['creditRating']).value,
      activeFlag: this.editForm.get(['activeFlag']).value,
      avatarContentType: this.editForm.get(['avatarContentType']).value,
      avatar: this.editForm.get(['avatar']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value,
      primaryContactPersonId: this.editForm.get(['primaryContactPersonId']).value,
      alternateContactPersonId: this.editForm.get(['alternateContactPersonId']).value,
      supplierCategoryId: this.editForm.get(['supplierCategoryId']).value,
      deliveryMethodId: this.editForm.get(['deliveryMethodId']).value,
      deliveryCityId: this.editForm.get(['deliveryCityId']).value,
      postalCityId: this.editForm.get(['postalCityId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuppliers>>) {
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

  trackSupplierCategoriesById(index: number, item: ISupplierCategories) {
    return item.id;
  }

  trackDeliveryMethodsById(index: number, item: IDeliveryMethods) {
    return item.id;
  }

  trackCitiesById(index: number, item: ICities) {
    return item.id;
  }
}
