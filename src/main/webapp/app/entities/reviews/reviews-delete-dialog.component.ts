import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';

@Component({
  selector: 'jhi-reviews-delete-dialog',
  templateUrl: './reviews-delete-dialog.component.html'
})
export class ReviewsDeleteDialogComponent {
  reviews: IReviews;

  constructor(protected reviewsService: ReviewsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.reviewsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'reviewsListModification',
        content: 'Deleted an reviews'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-reviews-delete-popup',
  template: ''
})
export class ReviewsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reviews }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReviewsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.reviews = reviews;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/reviews', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/reviews', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
