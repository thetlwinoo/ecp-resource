import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from './product-option-set.service';

@Component({
  selector: 'jhi-product-option-set-delete-dialog',
  templateUrl: './product-option-set-delete-dialog.component.html'
})
export class ProductOptionSetDeleteDialogComponent {
  productOptionSet: IProductOptionSet;

  constructor(
    protected productOptionSetService: ProductOptionSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productOptionSetService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productOptionSetListModification',
        content: 'Deleted an productOptionSet'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-option-set-delete-popup',
  template: ''
})
export class ProductOptionSetDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productOptionSet }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductOptionSetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productOptionSet = productOptionSet;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-option-set', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-option-set', { outlets: { popup: null } }]);
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
