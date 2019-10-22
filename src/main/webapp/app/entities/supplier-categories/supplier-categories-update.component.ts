import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ISupplierCategories, SupplierCategories } from 'app/shared/model/supplier-categories.model';
import { SupplierCategoriesService } from './supplier-categories.service';

@Component({
  selector: 'jhi-supplier-categories-update',
  templateUrl: './supplier-categories-update.component.html'
})
export class SupplierCategoriesUpdateComponent implements OnInit {
  isSaving: boolean;
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    supplierCategoryName: [null, [Validators.required]],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(
    protected supplierCategoriesService: SupplierCategoriesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ supplierCategories }) => {
      this.updateForm(supplierCategories);
    });
  }

  updateForm(supplierCategories: ISupplierCategories) {
    this.editForm.patchValue({
      id: supplierCategories.id,
      supplierCategoryName: supplierCategories.supplierCategoryName,
      validFrom: supplierCategories.validFrom,
      validTo: supplierCategories.validTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const supplierCategories = this.createFromForm();
    if (supplierCategories.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierCategoriesService.update(supplierCategories));
    } else {
      this.subscribeToSaveResponse(this.supplierCategoriesService.create(supplierCategories));
    }
  }

  private createFromForm(): ISupplierCategories {
    return {
      ...new SupplierCategories(),
      id: this.editForm.get(['id']).value,
      supplierCategoryName: this.editForm.get(['supplierCategoryName']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierCategories>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
