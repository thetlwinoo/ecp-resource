import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { SupplierImportedDocumentService } from './supplier-imported-document.service';

@Component({
  selector: 'jhi-supplier-imported-document-delete-dialog',
  templateUrl: './supplier-imported-document-delete-dialog.component.html'
})
export class SupplierImportedDocumentDeleteDialogComponent {
  supplierImportedDocument: ISupplierImportedDocument;

  constructor(
    protected supplierImportedDocumentService: SupplierImportedDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supplierImportedDocumentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'supplierImportedDocumentListModification',
        content: 'Deleted an supplierImportedDocument'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-supplier-imported-document-delete-popup',
  template: ''
})
export class SupplierImportedDocumentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierImportedDocument }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SupplierImportedDocumentDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.supplierImportedDocument = supplierImportedDocument;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/supplier-imported-document', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/supplier-imported-document', { outlets: { popup: null } }]);
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
