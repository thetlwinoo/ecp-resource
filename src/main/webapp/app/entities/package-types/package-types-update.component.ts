import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IPackageTypes, PackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from './package-types.service';

@Component({
  selector: 'jhi-package-types-update',
  templateUrl: './package-types-update.component.html'
})
export class PackageTypesUpdateComponent implements OnInit {
  isSaving: boolean;
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    packageTypeName: [null, [Validators.required]],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(protected packageTypesService: PackageTypesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ packageTypes }) => {
      this.updateForm(packageTypes);
    });
  }

  updateForm(packageTypes: IPackageTypes) {
    this.editForm.patchValue({
      id: packageTypes.id,
      packageTypeName: packageTypes.packageTypeName,
      validFrom: packageTypes.validFrom,
      validTo: packageTypes.validTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const packageTypes = this.createFromForm();
    if (packageTypes.id !== undefined) {
      this.subscribeToSaveResponse(this.packageTypesService.update(packageTypes));
    } else {
      this.subscribeToSaveResponse(this.packageTypesService.create(packageTypes));
    }
  }

  private createFromForm(): IPackageTypes {
    return {
      ...new PackageTypes(),
      id: this.editForm.get(['id']).value,
      packageTypeName: this.editForm.get(['packageTypeName']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPackageTypes>>) {
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
