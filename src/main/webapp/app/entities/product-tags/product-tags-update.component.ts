import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductTags, ProductTags } from 'app/shared/model/product-tags.model';
import { ProductTagsService } from './product-tags.service';
import { IProducts } from 'app/shared/model/products.model';
import { ProductsService } from 'app/entities/products/products.service';

@Component({
  selector: 'jhi-product-tags-update',
  templateUrl: './product-tags-update.component.html'
})
export class ProductTagsUpdateComponent implements OnInit {
  isSaving: boolean;

  products: IProducts[];

  editForm = this.fb.group({
    id: [],
    tagName: [null, [Validators.required]],
    productId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productTagsService: ProductTagsService,
    protected productsService: ProductsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productTags }) => {
      this.updateForm(productTags);
    });
    this.productsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducts[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducts[]>) => response.body)
      )
      .subscribe((res: IProducts[]) => (this.products = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productTags: IProductTags) {
    this.editForm.patchValue({
      id: productTags.id,
      tagName: productTags.tagName,
      productId: productTags.productId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productTags = this.createFromForm();
    if (productTags.id !== undefined) {
      this.subscribeToSaveResponse(this.productTagsService.update(productTags));
    } else {
      this.subscribeToSaveResponse(this.productTagsService.create(productTags));
    }
  }

  private createFromForm(): IProductTags {
    return {
      ...new ProductTags(),
      id: this.editForm.get(['id']).value,
      tagName: this.editForm.get(['tagName']).value,
      productId: this.editForm.get(['productId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductTags>>) {
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

  trackProductsById(index: number, item: IProducts) {
    return item.id;
  }
}
