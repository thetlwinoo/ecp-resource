import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductOption, ProductOption } from 'app/shared/model/product-option.model';
import { ProductOptionService } from './product-option.service';
import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from 'app/entities/product-option-set/product-option-set.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/suppliers.service';

@Component({
  selector: 'jhi-product-option-update',
  templateUrl: './product-option-update.component.html'
})
export class ProductOptionUpdateComponent implements OnInit {
  isSaving: boolean;

  productoptionsets: IProductOptionSet[];

  suppliers: ISuppliers[];

  editForm = this.fb.group({
    id: [],
    productOptionValue: [null, [Validators.required]],
    productOptionSetId: [],
    supplierId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productOptionService: ProductOptionService,
    protected productOptionSetService: ProductOptionSetService,
    protected suppliersService: SuppliersService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productOption }) => {
      this.updateForm(productOption);
    });
    this.productOptionSetService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProductOptionSet[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductOptionSet[]>) => response.body)
      )
      .subscribe((res: IProductOptionSet[]) => (this.productoptionsets = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.suppliersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISuppliers[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISuppliers[]>) => response.body)
      )
      .subscribe((res: ISuppliers[]) => (this.suppliers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productOption: IProductOption) {
    this.editForm.patchValue({
      id: productOption.id,
      productOptionValue: productOption.productOptionValue,
      productOptionSetId: productOption.productOptionSetId,
      supplierId: productOption.supplierId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productOption = this.createFromForm();
    if (productOption.id !== undefined) {
      this.subscribeToSaveResponse(this.productOptionService.update(productOption));
    } else {
      this.subscribeToSaveResponse(this.productOptionService.create(productOption));
    }
  }

  private createFromForm(): IProductOption {
    return {
      ...new ProductOption(),
      id: this.editForm.get(['id']).value,
      productOptionValue: this.editForm.get(['productOptionValue']).value,
      productOptionSetId: this.editForm.get(['productOptionSetId']).value,
      supplierId: this.editForm.get(['supplierId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductOption>>) {
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

  trackProductOptionSetById(index: number, item: IProductOptionSet) {
    return item.id;
  }

  trackSuppliersById(index: number, item: ISuppliers) {
    return item.id;
  }
}
