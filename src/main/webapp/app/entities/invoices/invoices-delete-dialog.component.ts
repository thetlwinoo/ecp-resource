import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInvoices } from 'app/shared/model/invoices.model';
import { InvoicesService } from './invoices.service';

@Component({
  selector: 'jhi-invoices-delete-dialog',
  templateUrl: './invoices-delete-dialog.component.html'
})
export class InvoicesDeleteDialogComponent {
  invoices: IInvoices;

  constructor(protected invoicesService: InvoicesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.invoicesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'invoicesListModification',
        content: 'Deleted an invoices'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-invoices-delete-popup',
  template: ''
})
export class InvoicesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ invoices }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(InvoicesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.invoices = invoices;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/invoices', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/invoices', { outlets: { popup: null } }]);
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
