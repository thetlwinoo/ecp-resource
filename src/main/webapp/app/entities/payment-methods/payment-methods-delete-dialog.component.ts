import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentMethods } from 'app/shared/model/payment-methods.model';
import { PaymentMethodsService } from './payment-methods.service';

@Component({
  selector: 'jhi-payment-methods-delete-dialog',
  templateUrl: './payment-methods-delete-dialog.component.html'
})
export class PaymentMethodsDeleteDialogComponent {
  paymentMethods: IPaymentMethods;

  constructor(
    protected paymentMethodsService: PaymentMethodsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paymentMethodsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'paymentMethodsListModification',
        content: 'Deleted an paymentMethods'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-payment-methods-delete-popup',
  template: ''
})
export class PaymentMethodsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paymentMethods }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PaymentMethodsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.paymentMethods = paymentMethods;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/payment-methods', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/payment-methods', { outlets: { popup: null } }]);
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
