import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompareProducts } from 'app/shared/model/compare-products.model';
import { CompareProductsService } from './compare-products.service';

@Component({
  selector: 'jhi-compare-products-delete-dialog',
  templateUrl: './compare-products-delete-dialog.component.html'
})
export class CompareProductsDeleteDialogComponent {
  compareProducts: ICompareProducts;

  constructor(
    protected compareProductsService: CompareProductsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.compareProductsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'compareProductsListModification',
        content: 'Deleted an compareProducts'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compare-products-delete-popup',
  template: ''
})
export class CompareProductsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compareProducts }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompareProductsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.compareProducts = compareProducts;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compare-products', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compare-products', { outlets: { popup: null } }]);
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
