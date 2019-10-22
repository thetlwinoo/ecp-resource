import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProducts, Products } from 'app/shared/model/products.model';
import { ProductsService } from './products.service';
import { IProductDocument } from 'app/shared/model/product-document.model';
import { ProductDocumentService } from 'app/entities/product-document/product-document.service';
import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from 'app/entities/suppliers/suppliers.service';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { ProductCategoryService } from 'app/entities/product-category/product-category.service';
import { IProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from 'app/entities/product-brand/product-brand.service';

@Component({
  selector: 'jhi-products-update',
  templateUrl: './products-update.component.html'
})
export class ProductsUpdateComponent implements OnInit {
  isSaving: boolean;

  documents: IProductDocument[];

  suppliers: ISuppliers[];

  productcategories: IProductCategory[];

  productbrands: IProductBrand[];
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    productName: [null, [Validators.required]],
    handle: [],
    productNumber: [],
    searchDetails: [],
    sellCount: [],
    activeInd: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    documentId: [],
    supplierId: [],
    productCategoryId: [],
    productBrandId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productsService: ProductsService,
    protected productDocumentService: ProductDocumentService,
    protected suppliersService: SuppliersService,
    protected productCategoryService: ProductCategoryService,
    protected productBrandService: ProductBrandService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ products }) => {
      this.updateForm(products);
    });
    this.productDocumentService
      .query({ 'productId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IProductDocument[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductDocument[]>) => response.body)
      )
      .subscribe(
        (res: IProductDocument[]) => {
          if (!this.editForm.get('documentId').value) {
            this.documents = res;
          } else {
            this.productDocumentService
              .find(this.editForm.get('documentId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IProductDocument>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IProductDocument>) => subResponse.body)
              )
              .subscribe(
                (subRes: IProductDocument) => (this.documents = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.suppliersService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISuppliers[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISuppliers[]>) => response.body)
      )
      .subscribe((res: ISuppliers[]) => (this.suppliers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productCategoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProductCategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductCategory[]>) => response.body)
      )
      .subscribe((res: IProductCategory[]) => (this.productcategories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productBrandService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProductBrand[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProductBrand[]>) => response.body)
      )
      .subscribe((res: IProductBrand[]) => (this.productbrands = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(products: IProducts) {
    this.editForm.patchValue({
      id: products.id,
      productName: products.productName,
      handle: products.handle,
      productNumber: products.productNumber,
      searchDetails: products.searchDetails,
      sellCount: products.sellCount,
      activeInd: products.activeInd,
      lastEditedBy: products.lastEditedBy,
      lastEditedWhen: products.lastEditedWhen,
      documentId: products.documentId,
      supplierId: products.supplierId,
      productCategoryId: products.productCategoryId,
      productBrandId: products.productBrandId
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

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const products = this.createFromForm();
    if (products.id !== undefined) {
      this.subscribeToSaveResponse(this.productsService.update(products));
    } else {
      this.subscribeToSaveResponse(this.productsService.create(products));
    }
  }

  private createFromForm(): IProducts {
    return {
      ...new Products(),
      id: this.editForm.get(['id']).value,
      productName: this.editForm.get(['productName']).value,
      handle: this.editForm.get(['handle']).value,
      productNumber: this.editForm.get(['productNumber']).value,
      searchDetails: this.editForm.get(['searchDetails']).value,
      sellCount: this.editForm.get(['sellCount']).value,
      activeInd: this.editForm.get(['activeInd']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      documentId: this.editForm.get(['documentId']).value,
      supplierId: this.editForm.get(['supplierId']).value,
      productCategoryId: this.editForm.get(['productCategoryId']).value,
      productBrandId: this.editForm.get(['productBrandId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducts>>) {
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

  trackProductDocumentById(index: number, item: IProductDocument) {
    return item.id;
  }

  trackSuppliersById(index: number, item: ISuppliers) {
    return item.id;
  }

  trackProductCategoryById(index: number, item: IProductCategory) {
    return item.id;
  }

  trackProductBrandById(index: number, item: IProductBrand) {
    return item.id;
  }
}
