import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductCatalog } from 'app/shared/model/product-catalog.model';
import { ProductCatalogService } from './product-catalog.service';

@Component({
  selector: 'jhi-product-catalog-delete-dialog',
  templateUrl: './product-catalog-delete-dialog.component.html'
})
export class ProductCatalogDeleteDialogComponent {
  productCatalog: IProductCatalog;

  constructor(
    protected productCatalogService: ProductCatalogService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productCatalogService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productCatalogListModification',
        content: 'Deleted an productCatalog'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-catalog-delete-popup',
  template: ''
})
export class ProductCatalogDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productCatalog }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductCatalogDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productCatalog = productCatalog;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-catalog', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-catalog', { outlets: { popup: null } }]);
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
