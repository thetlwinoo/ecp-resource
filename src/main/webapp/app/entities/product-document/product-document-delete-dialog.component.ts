import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductDocument } from 'app/shared/model/product-document.model';
import { ProductDocumentService } from './product-document.service';

@Component({
  selector: 'jhi-product-document-delete-dialog',
  templateUrl: './product-document-delete-dialog.component.html'
})
export class ProductDocumentDeleteDialogComponent {
  productDocument: IProductDocument;

  constructor(
    protected productDocumentService: ProductDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productDocumentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productDocumentListModification',
        content: 'Deleted an productDocument'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-document-delete-popup',
  template: ''
})
export class ProductDocumentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productDocument }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductDocumentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productDocument = productDocument;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-document', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-document', { outlets: { popup: null } }]);
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
