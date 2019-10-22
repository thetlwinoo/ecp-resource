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
import { IShoppingCartItems, ShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';
import { ShoppingCartItemsService } from './shopping-cart-items.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from 'app/entities/shopping-carts/shopping-carts.service';

@Component({
  selector: 'jhi-shopping-cart-items-update',
  templateUrl: './shopping-cart-items-update.component.html'
})
export class ShoppingCartItemsUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitems: IStockItems[];

  shoppingcarts: IShoppingCarts[];
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    quantity: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    stockItemId: [],
    cartId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected shoppingCartItemsService: ShoppingCartItemsService,
    protected stockItemsService: StockItemsService,
    protected shoppingCartsService: ShoppingCartsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ shoppingCartItems }) => {
      this.updateForm(shoppingCartItems);
    });
    this.stockItemsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStockItems[]>) => response.body)
      )
      .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.shoppingCartsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IShoppingCarts[]>) => mayBeOk.ok),
        map((response: HttpResponse<IShoppingCarts[]>) => response.body)
      )
      .subscribe((res: IShoppingCarts[]) => (this.shoppingcarts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(shoppingCartItems: IShoppingCartItems) {
    this.editForm.patchValue({
      id: shoppingCartItems.id,
      quantity: shoppingCartItems.quantity,
      lastEditedBy: shoppingCartItems.lastEditedBy,
      lastEditedWhen: shoppingCartItems.lastEditedWhen,
      stockItemId: shoppingCartItems.stockItemId,
      cartId: shoppingCartItems.cartId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const shoppingCartItems = this.createFromForm();
    if (shoppingCartItems.id !== undefined) {
      this.subscribeToSaveResponse(this.shoppingCartItemsService.update(shoppingCartItems));
    } else {
      this.subscribeToSaveResponse(this.shoppingCartItemsService.create(shoppingCartItems));
    }
  }

  private createFromForm(): IShoppingCartItems {
    return {
      ...new ShoppingCartItems(),
      id: this.editForm.get(['id']).value,
      quantity: this.editForm.get(['quantity']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      stockItemId: this.editForm.get(['stockItemId']).value,
      cartId: this.editForm.get(['cartId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShoppingCartItems>>) {
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

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }

  trackShoppingCartsById(index: number, item: IShoppingCarts) {
    return item.id;
  }
}
