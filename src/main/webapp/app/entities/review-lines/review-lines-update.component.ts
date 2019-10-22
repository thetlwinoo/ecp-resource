import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IReviewLines, ReviewLines } from 'app/shared/model/review-lines.model';
import { ReviewLinesService } from './review-lines.service';
import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from 'app/entities/stock-items/stock-items.service';
import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from 'app/entities/reviews/reviews.service';

@Component({
  selector: 'jhi-review-lines-update',
  templateUrl: './review-lines-update.component.html'
})
export class ReviewLinesUpdateComponent implements OnInit {
  isSaving: boolean;

  stockitems: IStockItems[];

  reviews: IReviews[];
  lastEditedWhenDp: any;

  editForm = this.fb.group({
    id: [],
    productRating: [],
    productReview: [],
    sellerRating: [],
    sellerReview: [],
    deliveryRating: [],
    deliveryReview: [],
    photo: [],
    photoContentType: [],
    lastEditedBy: [],
    lastEditedWhen: [],
    reviewId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected reviewLinesService: ReviewLinesService,
    protected stockItemsService: StockItemsService,
    protected reviewsService: ReviewsService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reviewLines }) => {
      this.updateForm(reviewLines);
    });
    this.stockItemsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IStockItems[]>) => mayBeOk.ok),
        map((response: HttpResponse<IStockItems[]>) => response.body)
      )
      .subscribe((res: IStockItems[]) => (this.stockitems = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.reviewsService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IReviews[]>) => mayBeOk.ok),
        map((response: HttpResponse<IReviews[]>) => response.body)
      )
      .subscribe((res: IReviews[]) => (this.reviews = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(reviewLines: IReviewLines) {
    this.editForm.patchValue({
      id: reviewLines.id,
      productRating: reviewLines.productRating,
      productReview: reviewLines.productReview,
      sellerRating: reviewLines.sellerRating,
      sellerReview: reviewLines.sellerReview,
      deliveryRating: reviewLines.deliveryRating,
      deliveryReview: reviewLines.deliveryReview,
      photo: reviewLines.photo,
      photoContentType: reviewLines.photoContentType,
      lastEditedBy: reviewLines.lastEditedBy,
      lastEditedWhen: reviewLines.lastEditedWhen,
      reviewId: reviewLines.reviewId
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
    const reviewLines = this.createFromForm();
    if (reviewLines.id !== undefined) {
      this.subscribeToSaveResponse(this.reviewLinesService.update(reviewLines));
    } else {
      this.subscribeToSaveResponse(this.reviewLinesService.create(reviewLines));
    }
  }

  private createFromForm(): IReviewLines {
    return {
      ...new ReviewLines(),
      id: this.editForm.get(['id']).value,
      productRating: this.editForm.get(['productRating']).value,
      productReview: this.editForm.get(['productReview']).value,
      sellerRating: this.editForm.get(['sellerRating']).value,
      sellerReview: this.editForm.get(['sellerReview']).value,
      deliveryRating: this.editForm.get(['deliveryRating']).value,
      deliveryReview: this.editForm.get(['deliveryReview']).value,
      photoContentType: this.editForm.get(['photoContentType']).value,
      photo: this.editForm.get(['photo']).value,
      lastEditedBy: this.editForm.get(['lastEditedBy']).value,
      lastEditedWhen: this.editForm.get(['lastEditedWhen']).value,
      reviewId: this.editForm.get(['reviewId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReviewLines>>) {
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

  trackStockItemsById(index: number, item: IStockItems) {
    return item.id;
  }

  trackReviewsById(index: number, item: IReviews) {
    return item.id;
  }
}
