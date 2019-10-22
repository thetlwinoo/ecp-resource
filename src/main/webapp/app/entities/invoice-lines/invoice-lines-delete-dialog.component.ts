import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoiceLines } from 'app/shared/model/invoice-lines.model';
import { InvoiceLinesService } from './invoice-lines.service';

@Component({
  selector: 'jhi-invoice-lines-delete-dialog',
  templateUrl: './invoice-lines-delete-dialog.component.html'
})
export class InvoiceLinesDeleteDialogComponent {
  invoiceLines: IInvoiceLines;

  constructor(
    protected invoiceLinesService: InvoiceLinesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.invoiceLinesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'invoiceLinesListModification',
        content: 'Deleted an invoiceLines'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-invoice-lines-delete-popup',
  template: ''
})
export class InvoiceLinesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invoiceLines }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InvoiceLinesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.invoiceLines = invoiceLines;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/invoice-lines', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/invoice-lines', { outlets: { popup: null } }]);
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
