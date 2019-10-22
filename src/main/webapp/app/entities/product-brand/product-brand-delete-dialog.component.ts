import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductBrand } from 'app/shared/model/product-brand.model';
import { ProductBrandService } from './product-brand.service';

@Component({
  selector: 'jhi-product-brand-delete-dialog',
  templateUrl: './product-brand-delete-dialog.component.html'
})
export class ProductBrandDeleteDialogComponent {
  productBrand: IProductBrand;

  constructor(
    protected productBrandService: ProductBrandService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productBrandService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productBrandListModification',
        content: 'Deleted an productBrand'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-brand-delete-popup',
  template: ''
})
export class ProductBrandDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productBrand }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductBrandDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productBrand = productBrand;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-brand', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-brand', { outlets: { popup: null } }]);
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
