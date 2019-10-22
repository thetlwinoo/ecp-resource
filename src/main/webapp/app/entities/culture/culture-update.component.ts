import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICulture, Culture } from 'app/shared/model/culture.model';
import { CultureService } from './culture.service';

@Component({
  selector: 'jhi-culture-update',
  templateUrl: './culture-update.component.html'
})
export class CultureUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    cultureCode: [null, [Validators.required]],
    cultureName: [null, [Validators.required]]
  });

  constructor(protected cultureService: CultureService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ culture }) => {
      this.updateForm(culture);
    });
  }

  updateForm(culture: ICulture) {
    this.editForm.patchValue({
      id: culture.id,
      cultureCode: culture.cultureCode,
      cultureName: culture.cultureName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const culture = this.createFromForm();
    if (culture.id !== undefined) {
      this.subscribeToSaveResponse(this.cultureService.update(culture));
    } else {
      this.subscribeToSaveResponse(this.cultureService.create(culture));
    }
  }

  private createFromForm(): ICulture {
    return {
      ...new Culture(),
      id: this.editForm.get(['id']).value,
      cultureCode: this.editForm.get(['cultureCode']).value,
      cultureName: this.editForm.get(['cultureName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICulture>>) {
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
