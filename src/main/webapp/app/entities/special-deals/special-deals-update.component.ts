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
import { ISpecialDeals, SpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from './special-deals.service';
import { IBuyingGroups } from 'app/shared/model/buying-groups.model';
import { BuyingGroupsService } from 'app/entities/buying-groups/buying-groups.service';
import { ICustomerCategories } from 'app/shared/model/customer-categories.model';
import { CustomerCategoriesService } from 'app/entities/customer-categories/customer-categories.service';
import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from 'app/entities/customers/customers.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';

@Component({
  selector: 'jhi-special-deals-update',
  templateUrl: './special-deals-update.component.html'
})
export class SpecialDealsUpdateComponent implements OnInit {
  isSaving: boolean;

  buyinggroups: IBuyingGroups[];

  customercategories: ICustomerCategories[];

  customers: ICustomers[];

  productcategories: IProductCategory[];

  stockitems: IStockItems[];
  startDateDp: any;
  endDateDp: any;
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    dealDescription: [null, [Validators.required]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    discountAmount: [],
    discountPercentage: [],
    discountCode: [],
    unitPrice: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    buyingGroupId: [],
    customerCategoryId: [],
    customerId: [],
    productCategoryId: [],
    stockItemId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected specialDealsService: SpecialDealsService,
    protected buyingGroupsService: BuyingGroupsService,
    protected customerCategoriesService: CustomerCategoriesService,
    protected customersService: CustomersService,
    protected productCategoryService: ProductCategoryService,
    protected stockItemsService: StockItemsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ specialDeals }) => {
      this.updateForm(specialDeals);
    });
    this.buyingGroupsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBuyingGroups[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBuyingGroups[]>) => response.body)
      )
      .subscribe((res: IBuyingGroups[]) => (this.buyinggroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.customerCategoriesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomerCategories[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomerCategories[]>) => response.body)
      )
      .subscribe((res: ICustomerCategories[]) => (this.customercategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.customersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICustomers[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICustomers[]>) => response.body)
      )
      .subscribe((res: ICustomers[]) => (this.customers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productCategoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductCategory[]>) => response.body)
      )
      .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.stockItemsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStockItems[]>) => response.body)
      )
      .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(specialDeals: ISpecialDeals) {
    this.editForm.patchValue({
      id: specialDeals.id,
      dealDescription: specialDeals.dealDescription,
      startDate: specialDeals.startDate,
      endDate: specialDeals.endDate,
      discountAmount: specialDeals.discountAmount,
      discountPercentage: specialDeals.discountPercentage,
      discountCode: specialDeals.discountCode,
      unitPrice: specialDeals.unitPrice,
      lastEditedBy: specialDeals.lastEditedBy,
      lastEditedWhen: specialDeals.lastEditedWhen,
      buyingGroupId: specialDeals.buyingGroupId,
      customerCategoryId: specialDeals.customerCategoryId,
      customerId: specialDeals.customerId,
      productCategoryId: specialDeals.productCategoryId,
      stockItemId: specialDeals.stockItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const specialDeals = this.createFromForm();
    if (specialDeals.id !== undefined) {
      this.subscribeToSaveResponse(this.specialDealsService.update(specialDeals));
    } else {
      this.subscribeToSaveResponse(this.specialDealsService.create(specialDeals));
    }
  }

  private createFromForm(): ISpecialDeals {
    return {
      ...new SpecialDeals(),
      id: this.editForm.get(['id']).value,
      dealDescription: this.editForm.get(['dealDescription']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      discountAmount: this.editForm.get(['discountAmount']).value,
      discountPercentage: this.editForm.get(['discountPercentage']).value,
      discountCode: this.editForm.get(['discountCode']).value,
      unitPrice: this.editForm.get(['unitPrice']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      buyingGroupId: this.editForm.get(['buyingGroupId']).value,
      customerCategoryId: this.editForm.get(['customerCategoryId']).value,
      customerId: this.editForm.get(['customerId']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value,
      stockItemId: this.editForm.get(['stockItemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpecialDeals>>) {
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

  trackBuyingGroupsById(index: number, item: IBuyingGroups) {
    return item.id;
  }

  trackCustomerCategoriesById(index: number, item: ICustomerCategories) {
    return item.id;
  }

  trackCustomersById(index: number, item: ICustomers) {
    return item.id;
  }

  trackProductCategoryById(index: number, item: IProductCategory) {
    return item.id;
  }

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }
}
