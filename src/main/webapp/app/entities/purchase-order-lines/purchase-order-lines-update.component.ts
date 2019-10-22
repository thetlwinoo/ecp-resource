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
import { IPurchaseOrderLines, PurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';
import { PurchaseOrderLinesService } from './purchase-order-lines.service';
import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from 'app/entities/package-types/package-types.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from 'app/entities/purchase-orders/purchase-orders.service';

@Component({
  selector: 'jhi-purchase-order-lines-update',
  templateUrl: './purchase-order-lines-update.component.html'
})
export class PurchaseOrderLinesUpdateComponent implements OnInit {
  isSaving: boolean;

  packagetypes: IPackageTypes[];

  stockitems: IStockItems[];

  purchaseorders: IPurchaseOrders[];
  lastReceiptDateDp: any;
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    ordersOuters: [null, [Validators.required]],
    description: [],
    receivedOuters: [],
    expectedUnitPricePerOuter: [],
    lastReceiptDate: [],
    isOrderLineFinalized: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    packageTypeId: [],
    stockItemId: [],
    purchaseOrderId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected purchaseOrderLinesService: PurchaseOrderLinesService,
    protected packageTypesService: PackageTypesService,
    protected stockItemsService: StockItemsService,
    protected purchaseOrdersService: PurchaseOrdersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ purchaseOrderLines }) => {
      this.updateForm(purchaseOrderLines);
    });
    this.packageTypesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPackageTypes[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPackageTypes[]>) => response.body)
      )
      .subscribe((res: IPackageTypes[]) => (this.packagetypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.stockItemsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStockItems[]>) => response.body)
      )
      .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.purchaseOrdersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPurchaseOrders[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPurchaseOrders[]>) => response.body)
      )
      .subscribe((res: IPurchaseOrders[]) => (this.purchaseorders = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(purchaseOrderLines: IPurchaseOrderLines) {
    this.editForm.patchValue({
      id: purchaseOrderLines.id,
      ordersOuters: purchaseOrderLines.ordersOuters,
      description: purchaseOrderLines.description,
      receivedOuters: purchaseOrderLines.receivedOuters,
      expectedUnitPricePerOuter: purchaseOrderLines.expectedUnitPricePerOuter,
      lastReceiptDate: purchaseOrderLines.lastReceiptDate,
      isOrderLineFinalized: purchaseOrderLines.isOrderLineFinalized,
      lastEditedBy: purchaseOrderLines.lastEditedBy,
      lastEditedWhen: purchaseOrderLines.lastEditedWhen,
      packageTypeId: purchaseOrderLines.packageTypeId,
      stockItemId: purchaseOrderLines.stockItemId,
      purchaseOrderId: purchaseOrderLines.purchaseOrderId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const purchaseOrderLines = this.createFromForm();
    if (purchaseOrderLines.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseOrderLinesService.update(purchaseOrderLines));
    } else {
      this.subscribeToSaveResponse(this.purchaseOrderLinesService.create(purchaseOrderLines));
    }
  }

  private createFromForm(): IPurchaseOrderLines {
    return {
      ...new PurchaseOrderLines(),
      id: this.editForm.get(['id']).value,
      ordersOuters: this.editForm.get(['ordersOuters']).value,
      description: this.editForm.get(['description']).value,
      receivedOuters: this.editForm.get(['receivedOuters']).value,
      expectedUnitPricePerOuter: this.editForm.get(['expectedUnitPricePerOuter']).value,
      lastReceiptDate: this.editForm.get(['lastReceiptDate']).value,
      isOrderLineFinalized: this.editForm.get(['isOrderLineFinalized']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      packageTypeId: this.editForm.get(['packageTypeId']).value,
      stockItemId: this.editForm.get(['stockItemId']).value,
      purchaseOrderId: this.editForm.get(['purchaseOrderId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseOrderLines>>) {
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

  trackPackageTypesById(index: number, item: IPackageTypes) {
    return item.id;
  }

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }

  trackPurchaseOrdersById(index: number, item: IPurchaseOrders) {
    return item.id;
  }
}
