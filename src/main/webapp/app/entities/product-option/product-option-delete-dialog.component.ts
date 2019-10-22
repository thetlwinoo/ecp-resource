import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductOption } from 'app/shared/model/product-option.model';
import { ProductOptionService } from './product-option.service';

@Component({
  selector: 'jhi-product-option-delete-dialog',
  templateUrl: './product-option-delete-dialog.component.html'
})
export class ProductOptionDeleteDialogComponent {
  productOption: IProductOption;

  constructor(
    protected productOptionService: ProductOptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productOptionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productOptionListModification',
        content: 'Deleted an productOption'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-option-delete-popup',
  template: ''
})
export class ProductOptionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productOption }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductOptionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productOption = productOption;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-option', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-option', { outlets: { popup: null } }]);
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
