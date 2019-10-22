import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';
import { BarcodeTypesService } from './barcode-types.service';

@Component({
  selector: 'jhi-barcode-types-delete-dialog',
  templateUrl: './barcode-types-delete-dialog.component.html'
})
export class BarcodeTypesDeleteDialogComponent {
  barcodeTypes: IBarcodeTypes;

  constructor(
    protected barcodeTypesService: BarcodeTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.barcodeTypesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'barcodeTypesListModification',
        content: 'Deleted an barcodeTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-barcode-types-delete-popup',
  template: ''
})
export class BarcodeTypesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ barcodeTypes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BarcodeTypesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.barcodeTypes = barcodeTypes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/barcode-types', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/barcode-types', { outlets: { popup: null } }]);
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
