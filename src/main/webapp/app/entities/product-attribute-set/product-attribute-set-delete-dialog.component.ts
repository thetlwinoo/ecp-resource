import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { ProductAttributeSetService } from './product-attribute-set.service';

@Component({
  selector: 'jhi-product-attribute-set-delete-dialog',
  templateUrl: './product-attribute-set-delete-dialog.component.html'
})
export class ProductAttributeSetDeleteDialogComponent {
  productAttributeSet: IProductAttributeSet;

  constructor(
    protected productAttributeSetService: ProductAttributeSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productAttributeSetService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productAttributeSetListModification',
        content: 'Deleted an productAttributeSet'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-attribute-set-delete-popup',
  template: ''
})
export class ProductAttributeSetDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productAttributeSet }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductAttributeSetDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.productAttributeSet = productAttributeSet;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-attribute-set', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-attribute-set', { outlets: { popup: null } }]);
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
