import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IReviewLines } from 'app/shared/model/review-lines.model';
import { AccountService } from 'app/core/auth/account.service';
import { ReviewLinesService } from './review-lines.service';

@Component({
  selector: 'jhi-review-lines',
  templateUrl: './review-lines.component.html'
})
export class ReviewLinesComponent implements OnInit, OnDestroy {
  reviewLines: IReviewLines[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected reviewLinesService: ReviewLinesService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.reviewLinesService
      .query()
      .pipe(
        filter((res: HttpResponse<IReviewLines[]>) => res.ok),
        map((res: HttpResponse<IReviewLines[]>) => res.body)
      )
      .subscribe((res: IReviewLines[]) => {
        this.reviewLines = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInReviewLines();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IReviewLines) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInReviewLines() {
    this.eventSubscriber = this.eventManager.subscribe('reviewLinesListModification', response => this.loadAll());
  }
}
