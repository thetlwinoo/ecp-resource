import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';
import { PurchaseOrderLinesService } from './purchase-order-lines.service';

@Component({
  selector: 'jhi-purchase-order-lines-delete-dialog',
  templateUrl: './purchase-order-lines-delete-dialog.component.html'
})
export class PurchaseOrderLinesDeleteDialogComponent {
  purchaseOrderLines: IPurchaseOrderLines;

  constructor(
    protected purchaseOrderLinesService: PurchaseOrderLinesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.purchaseOrderLinesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'purchaseOrderLinesListModification',
        content: 'Deleted an purchaseOrderLines'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-purchase-order-lines-delete-popup',
  template: ''
})
export class PurchaseOrderLinesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ purchaseOrderLines }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PurchaseOrderLinesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.purchaseOrderLines = purchaseOrderLines;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/purchase-order-lines', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/purchase-order-lines', { outlets: { popup: null } }]);
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
