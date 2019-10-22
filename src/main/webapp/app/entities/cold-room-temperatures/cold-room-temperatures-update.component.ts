import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { IColdRoomTemperatures, ColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';
import { ColdRoomTemperaturesService } from './cold-room-temperatures.service';

@Component({
  selector: 'jhi-cold-room-temperatures-update',
  templateUrl: './cold-room-temperatures-update.component.html'
})
export class ColdRoomTemperaturesUpdateComponent implements OnInit {
  isSaving: boolean;
  recordedWhenDp: any;
  validFromDp: any;
  validToDp: any;

  editForm = this.fb.group({
    id: [],
    coldRoomSensorNumber: [null, [Validators.required]],
    recordedWhen: [null, [Validators.required]],
    temperature: [null, [Validators.required]],
    validFrom: [null, [Validators.required]],
    validTo: [null, [Validators.required]]
  });

  constructor(
    protected coldRoomTemperaturesService: ColdRoomTemperaturesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ coldRoomTemperatures }) => {
      this.updateForm(coldRoomTemperatures);
    });
  }

  updateForm(coldRoomTemperatures: IColdRoomTemperatures) {
    this.editForm.patchValue({
      id: coldRoomTemperatures.id,
      coldRoomSensorNumber: coldRoomTemperatures.coldRoomSensorNumber,
      recordedWhen: coldRoomTemperatures.recordedWhen,
      temperature: coldRoomTemperatures.temperature,
      validFrom: coldRoomTemperatures.validFrom,
      validTo: coldRoomTemperatures.validTo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const coldRoomTemperatures = this.createFromForm();
    if (coldRoomTemperatures.id !== undefined) {
      this.subscribeToSaveResponse(this.coldRoomTemperaturesService.update(coldRoomTemperatures));
    } else {
      this.subscribeToSaveResponse(this.coldRoomTemperaturesService.create(coldRoomTemperatures));
    }
  }

  private createFromForm(): IColdRoomTemperatures {
    return {
      ...new ColdRoomTemperatures(),
      id: this.editForm.get(['id']).value,
      coldRoomSensorNumber: this.editForm.get(['coldRoomSensorNumber']).value,
      recordedWhen: this.editForm.get(['recordedWhen']).value,
      temperature: this.editForm.get(['temperature']).value,
      validFrom: this.editForm.get(['validFrom']).value,
      validTo: this.editForm.get(['validTo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IColdRoomTemperatures>>) {
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
