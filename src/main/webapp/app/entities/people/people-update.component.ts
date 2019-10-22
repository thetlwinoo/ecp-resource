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
import { IPeople, People } from 'app/shared/model/people.model';
import { PeopleService } from './people.service';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from 'app/entities/shopping-carts/shopping-carts.service';
import { IWishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from 'app/entities/wishlists/wishlists.service';
import { ICompares } from 'app/shared/model/compares.model';
import { ComparesService } from 'app/entities/compares/compares.service';

@Component({
  selector: 'jhi-people-update',
  templateUrl: './people-update.component.html'
})
export class PeopleUpdateComponent implements OnInit {
  isSaving: boolean;

  shoppingcarts: IShoppingCarts[];

  wishlists: IWishlists[];

  compares: ICompares[];
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    fullName: [null, [Validators.required]],
    preferredName: [null, [Validators.required]],
    searchName: [null, [Validators.required]],
    isPermittedToLogon: [null, [Validators.required]],
    logonName: [],
    isExternalLogonProvider: [null, [Validators.required]],
    isSystemUser: [null, [Validators.required]],
    isEmployee: [null, [Validators.required]],
    isSalesPerson: [null, [Validators.required]],
    isGuestUser: [null, [Validators.required]],
    emailPromotion: [null, [Validators.required]],
    userPreferences: [],
    phoneNumber: [],
    emailAddress: [],
    photo: [],
    customFields: [],
    otherLanguages: [],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected peopleService: PeopleService,
    protected shoppingCartsService: ShoppingCartsService,
    protected wishlistsService: WishlistsService,
    protected comparesService: ComparesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ people }) => {
      this.updateForm(people);
    });
    this.shoppingCartsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IShoppingCarts[]>) => mayBeOk.ok),
        map((response: HttpResponse<IShoppingCarts[]>) => response.body)
      )
      .subscribe((res: IShoppingCarts[]) => (this.shoppingcarts = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.wishlistsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IWishlists[]>) => mayBeOk.ok),
        map((response: HttpResponse<IWishlists[]>) => response.body)
      )
      .subscribe((res: IWishlists[]) => (this.wishlists = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.comparesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompares[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompares[]>) => response.body)
      )
      .subscribe((res: ICompares[]) => (this.compares = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(people: IPeople) {
    this.editForm.patchValue({
      id: people.id,
      fullName: people.fullName,
      preferredName: people.preferredName,
      searchName: people.searchName,
      isPermittedToLogon: people.isPermittedToLogon,
      logonName: people.logonName,
      isExternalLogonProvider: people.isExternalLogonProvider,
      isSystemUser: people.isSystemUser,
      isEmployee: people.isEmployee,
      isSalesPerson: people.isSalesPerson,
      isGuestUser: people.isGuestUser,
      emailPromotion: people.emailPromotion,
      userPreferences: people.userPreferences,
      phoneNumber: people.phoneNumber,
      emailAddress: people.emailAddress,
      photo: people.photo,
      customFields: people.customFields,
      otherLanguages: people.otherLanguages,
      validFrom: people.validFrom,
      validTo: people.validTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const people = this.createFromForm();
    if (people.id !== undefined) {
      this.subscribeToSaveResponse(this.peopleService.update(people));
    } else {
      this.subscribeToSaveResponse(this.peopleService.create(people));
    }
  }

  private createFromForm(): IPeople {
    return {
      ...new People(),
      id: this.editForm.get(['id']).value,
      fullName: this.editForm.get(['fullName']).value,
      preferredName: this.editForm.get(['preferredName']).value,
      searchName: this.editForm.get(['searchName']).value,
      isPermittedToLogon: this.editForm.get(['isPermittedToLogon']).value,
      logonName: this.editForm.get(['logonName']).value,
      isExternalLogonProvider: this.editForm.get(['isExternalLogonProvider']).value,
      isSystemUser: this.editForm.get(['isSystemUser']).value,
      isEmployee: this.editForm.get(['isEmployee']).value,
      isSalesPerson: this.editForm.get(['isSalesPerson']).value,
      isGuestUser: this.editForm.get(['isGuestUser']).value,
      emailPromotion: this.editForm.get(['emailPromotion']).value,
      userPreferences: this.editForm.get(['userPreferences']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      emailAddress: this.editForm.get(['emailAddress']).value,
      photo: this.editForm.get(['photo']).value,
      customFields: this.editForm.get(['customFields']).value,
      otherLanguages: this.editForm.get(['otherLanguages']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeople>>) {
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

  trackShoppingCartsById(index: number, item: IShoppingCarts) {
    return item.id;
  }

  trackWishlistsById(index: number, item: IWishlists) {
    return item.id;
  }

  trackComparesById(index: number, item: ICompares) {
    return item.id;
  }
}
