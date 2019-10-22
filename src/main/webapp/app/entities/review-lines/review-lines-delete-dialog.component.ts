import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReviewLines } from 'app/shared/model/review-lines.model';
import { ReviewLinesService } from './review-lines.service';

@Component({
  selector: 'jhi-review-lines-delete-dialog',
  templateUrl: './review-lines-delete-dialog.component.html'
})
export class ReviewLinesDeleteDialogComponent {
  reviewLines: IReviewLines;

  constructor(
    protected reviewLinesService: ReviewLinesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.reviewLinesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'reviewLinesListModification',
        content: 'Deleted an reviewLines'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-review-lines-delete-popup',
  template: ''
})
export class ReviewLinesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reviewLines }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReviewLinesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.reviewLines = reviewLines;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/review-lines', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/review-lines', { outlets: { popup: null } }]);
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
