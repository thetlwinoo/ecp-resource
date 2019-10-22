import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductSet } from 'app/shared/model/product-set.model';
import { ProductSetService } from './product-set.service';

@Component({
  selector: 'jhi-product-set-delete-dialog',
  templateUrl: './product-set-delete-dialog.component.html'
})
export class ProductSetDeleteDialogComponent {
  productSet: IProductSet;

  constructor(
    protected productSetService: ProductSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productSetService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productSetListModification',
        content: 'Deleted an productSet'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-set-delete-popup',
  template: ''
})
export class ProductSetDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productSet }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductSetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productSet = productSet;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-set', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-set', { outlets: { popup: null } }]);
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
