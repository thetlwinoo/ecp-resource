import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IReviews } from 'app/shared/model/reviews.model';
import { AccountService } from 'app/core/auth/account.service';
import { ReviewsService } from './reviews.service';

@Component({
  selector: 'jhi-reviews',
  templateUrl: './reviews.component.html'
})
export class ReviewsComponent implements OnInit, OnDestroy {
  reviews: IReviews[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected reviewsService: ReviewsService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.reviewsService
      .query()
      .pipe(
        filter((res: HttpResponse<IReviews[]>) => res.ok),
        map((res: HttpResponse<IReviews[]>) => res.body)
      )
      .subscribe((res: IReviews[]) => {
        this.reviews = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInReviews();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReviews) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInReviews() {
    this.eventSubscriber = this.eventManager.subscribe('reviewsListModification', response => this.loadAll());
  }
}
