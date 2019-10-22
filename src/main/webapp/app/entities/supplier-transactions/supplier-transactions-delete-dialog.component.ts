import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierTransactions } from 'app/shared/model/supplier-transactions.model';
import { SupplierTransactionsService } from './supplier-transactions.service';

@Component({
  selector: 'jhi-supplier-transactions-delete-dialog',
  templateUrl: './supplier-transactions-delete-dialog.component.html'
})
export class SupplierTransactionsDeleteDialogComponent {
  supplierTransactions: ISupplierTransactions;

  constructor(
    protected supplierTransactionsService: SupplierTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supplierTransactionsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'supplierTransactionsListModification',
        content: 'Deleted an supplierTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-supplier-transactions-delete-popup',
  template: ''
})
export class SupplierTransactionsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierTransactions }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SupplierTransactionsDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.supplierTransactions = supplierTransactions;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/supplier-transactions', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/supplier-transactions', { outlets: { popup: null } }]);
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
