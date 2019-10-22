import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from './purchase-orders.service';

@Component({
  selector: 'jhi-purchase-orders-delete-dialog',
  templateUrl: './purchase-orders-delete-dialog.component.html'
})
export class PurchaseOrdersDeleteDialogComponent {
  purchaseOrders: IPurchaseOrders;

  constructor(
    protected purchaseOrdersService: PurchaseOrdersService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.purchaseOrdersService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'purchaseOrdersListModification',
        content: 'Deleted an purchaseOrders'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-purchase-orders-delete-popup',
  template: ''
})
export class PurchaseOrdersDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ purchaseOrders }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PurchaseOrdersDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.purchaseOrders = purchaseOrders;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/purchase-orders', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/purchase-orders', { outlets: { popup: null } }]);
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
