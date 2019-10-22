import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IBuyingGroups, BuyingGroups } from 'app/shared/model/buying-groups.model';
import { BuyingGroupsService } from './buying-groups.service';

@Component({
  selector: 'jhi-buying-groups-update',
  templateUrl: './buying-groups-update.component.html'
})
export class BuyingGroupsUpdateComponent implements OnInit {
  isSaving: boolean;
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    buyingGroupName: [],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(protected buyingGroupsService: BuyingGroupsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ buyingGroups }) => {
      this.updateForm(buyingGroups);
    });
  }

  updateForm(buyingGroups: IBuyingGroups) {
    this.editForm.patchValue({
      id: buyingGroups.id,
      buyingGroupName: buyingGroups.buyingGroupName,
      validFrom: buyingGroups.validFrom,
      validTo: buyingGroups.validTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const buyingGroups = this.createFromForm();
    if (buyingGroups.id !== undefined) {
      this.subscribeToSaveResponse(this.buyingGroupsService.update(buyingGroups));
    } else {
      this.subscribeToSaveResponse(this.buyingGroupsService.create(buyingGroups));
    }
  }

  private createFromForm(): IBuyingGroups {
    return {
      ...new BuyingGroups(),
      id: this.editForm.get(['id']).value,
      buyingGroupName: this.editForm.get(['buyingGroupName']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBuyingGroups>>) {
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
