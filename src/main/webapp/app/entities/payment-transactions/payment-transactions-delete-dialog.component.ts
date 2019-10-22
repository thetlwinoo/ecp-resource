import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentTransactions } from 'app/shared/model/payment-transactions.model';
import { PaymentTransactionsService } from './payment-transactions.service';

@Component({
  selector: 'jhi-payment-transactions-delete-dialog',
  templateUrl: './payment-transactions-delete-dialog.component.html'
})
export class PaymentTransactionsDeleteDialogComponent {
  paymentTransactions: IPaymentTransactions;

  constructor(
    protected paymentTransactionsService: PaymentTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paymentTransactionsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'paymentTransactionsListModification',
        content: 'Deleted an paymentTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-payment-transactions-delete-popup',
  template: ''
})
export class PaymentTransactionsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paymentTransactions }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PaymentTransactionsDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.paymentTransactions = paymentTransactions;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/payment-transactions', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/payment-transactions', { outlets: { popup: null } }]);
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
