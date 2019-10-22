import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAttribute } from 'app/shared/model/product-attribute.model';
import { ProductAttributeService } from './product-attribute.service';

@Component({
  selector: 'jhi-product-attribute-delete-dialog',
  templateUrl: './product-attribute-delete-dialog.component.html'
})
export class ProductAttributeDeleteDialogComponent {
  productAttribute: IProductAttribute;

  constructor(
    protected productAttributeService: ProductAttributeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productAttributeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productAttributeListModification',
        content: 'Deleted an productAttribute'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-attribute-delete-popup',
  template: ''
})
export class ProductAttributeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productAttribute }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductAttributeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productAttribute = productAttribute;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-attribute', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-attribute', { outlets: { popup: null } }]);
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
