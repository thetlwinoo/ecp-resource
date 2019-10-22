import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerTransactions } from 'app/shared/model/customer-transactions.model';
import { CustomerTransactionsService } from './customer-transactions.service';

@Component({
  selector: 'jhi-customer-transactions-delete-dialog',
  templateUrl: './customer-transactions-delete-dialog.component.html'
})
export class CustomerTransactionsDeleteDialogComponent {
  customerTransactions: ICustomerTransactions;

  constructor(
    protected customerTransactionsService: CustomerTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerTransactionsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'customerTransactionsListModification',
        content: 'Deleted an customerTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-customer-transactions-delete-popup',
  template: ''
})
export class CustomerTransactionsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerTransactions }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CustomerTransactionsDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.customerTransactions = customerTransactions;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/customer-transactions', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/customer-transactions', { outlets: { popup: null } }]);
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
