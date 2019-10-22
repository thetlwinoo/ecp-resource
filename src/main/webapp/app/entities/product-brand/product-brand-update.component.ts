import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IProductBrand, ProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from './product-brand.service';

@Component({
  selector: 'jhi-product-brand-update',
  templateUrl: './product-brand-update.component.html'
})
export class ProductBrandUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    productBrandName: [null, [Validators.required]],
    photo: [],
    photoContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected productBrandService: ProductBrandService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productBrand }) => {
      this.updateForm(productBrand);
    });
  }

  updateForm(productBrand: IProductBrand) {
    this.editForm.patchValue({
      id: productBrand.id,
      productBrandName: productBrand.productBrandName,
      photo: productBrand.photo,
      photoContentType: productBrand.photoContentType
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
    const productBrand = this.createFromForm();
    if (productBrand.id !== undefined) {
      this.subscribeToSaveResponse(this.productBrandService.update(productBrand));
    } else {
      this.subscribeToSaveResponse(this.productBrandService.create(productBrand));
    }
  }

  private createFromForm(): IProductBrand {
    return {
      ...new ProductBrand(),
      id: this.editForm.get(['id']).value,
      productBrandName: this.editForm.get(['productBrandName']).value,
      photoContentType: this.editForm.get(['photoContentType']).value,
      photo: this.editForm.get(['photo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductBrand>>) {
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
}
