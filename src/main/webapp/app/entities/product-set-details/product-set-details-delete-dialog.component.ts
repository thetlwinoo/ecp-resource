import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductSetDetails } from 'app/shared/model/product-set-details.model';
import { ProductSetDetailsService } from './product-set-details.service';

@Component({
  selector: 'jhi-product-set-details-delete-dialog',
  templateUrl: './product-set-details-delete-dialog.component.html'
})
export class ProductSetDetailsDeleteDialogComponent {
  productSetDetails: IProductSetDetails;

  constructor(
    protected productSetDetailsService: ProductSetDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productSetDetailsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productSetDetailsListModification',
        content: 'Deleted an productSetDetails'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-set-details-delete-popup',
  template: ''
})
export class ProductSetDetailsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productSetDetails }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductSetDetailsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productSetDetails = productSetDetails;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-set-details', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-set-details', { outlets: { popup: null } }]);
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
