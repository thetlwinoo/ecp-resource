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
import { IShoppingCarts, ShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from './shopping-carts.service';
import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from 'app/entities/special-deals/special-deals.service';
import { IPeople } from 'app/shared/model/people.model';
import { PeopleService } from 'app/entities/people/people.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';

@Component({
  selector: 'jhi-shopping-carts-update',
  templateUrl: './shopping-carts-update.component.html'
})
export class ShoppingCartsUpdateComponent implements OnInit {
  isSaving: boolean;

  specialdeals: ISpecialDeals[];

  cartusers: IPeople[];

  customers: ICustomers[];
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    totalPrice: [],
    totalCargoPrice: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    specialDealsId: [],
    cartUserId: [],
    customerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected shoppingCartsService: ShoppingCartsService,
    protected specialDealsService: SpecialDealsService,
    protected peopleService: PeopleService,
    protected customersService: CustomersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ shoppingCarts }) => {
      this.updateForm(shoppingCarts);
    });
    this.specialDealsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISpecialDeals[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISpecialDeals[]>) => response.body)
      )
      .subscribe((res: ISpecialDeals[]) => (this.specialdeals = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.peopleService
      .query({ 'cartId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPeople[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPeople[]>) => response.body)
      )
      .subscribe(
        (res: IPeople[]) => {
          if (!this.editForm.get('cartUserId').value) {
            this.cartusers = res;
          } else {
            this.peopleService
              .find(this.editForm.get('cartUserId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPeople>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPeople>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPeople) => (this.cartusers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.customersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomers[]>) => response.body)
      )
      .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(shoppingCarts: IShoppingCarts) {
    this.editForm.patchValue({
      id: shoppingCarts.id,
      totalPrice: shoppingCarts.totalPrice,
      totalCargoPrice: shoppingCarts.totalCargoPrice,
      lastEditedBy: shoppingCarts.lastEditedBy,
      lastEditedWhen: shoppingCarts.lastEditedWhen,
      specialDealsId: shoppingCarts.specialDealsId,
      cartUserId: shoppingCarts.cartUserId,
      customerId: shoppingCarts.customerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const shoppingCarts = this.createFromForm();
    if (shoppingCarts.id !== undefined) {
      this.subscribeToSaveResponse(this.shoppingCartsService.update(shoppingCarts));
    } else {
      this.subscribeToSaveResponse(this.shoppingCartsService.create(shoppingCarts));
    }
  }

  private createFromForm(): IShoppingCarts {
    return {
      ...new ShoppingCarts(),
      id: this.editForm.get(['id']).value,
      totalPrice: this.editForm.get(['totalPrice']).value,
      totalCargoPrice: this.editForm.get(['totalCargoPrice']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      specialDealsId: this.editForm.get(['specialDealsId']).value,
      cartUserId: this.editForm.get(['cartUserId']).value,
      customerId: this.editForm.get(['customerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShoppingCarts>>) {
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

  trackSpecialDealsById(index: number, item: ISpecialDeals) {
    return item.id;
  }

  trackPeopleById(index: number, item: IPeople) {
    return item.id;
  }

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }
}
