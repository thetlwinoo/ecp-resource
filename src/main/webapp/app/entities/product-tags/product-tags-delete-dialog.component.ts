import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductTags } from 'app/shared/model/product-tags.model';
import { ProductTagsService } from './product-tags.service';

@Component({
  selector: 'jhi-product-tags-delete-dialog',
  templateUrl: './product-tags-delete-dialog.component.html'
})
export class ProductTagsDeleteDialogComponent {
  productTags: IProductTags;

  constructor(
    protected productTagsService: ProductTagsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productTagsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productTagsListModification',
        content: 'Deleted an productTags'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-tags-delete-popup',
  template: ''
})
export class ProductTagsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productTags }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductTagsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productTags = productTags;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-tags', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-tags', { outlets: { popup: null } }]);
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
