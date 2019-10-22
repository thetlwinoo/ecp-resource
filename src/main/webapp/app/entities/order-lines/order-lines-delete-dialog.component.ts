import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrderLines } from 'app/shared/model/order-lines.model';
import { OrderLinesService } from './order-lines.service';

@Component({
  selector: 'jhi-order-lines-delete-dialog',
  templateUrl: './order-lines-delete-dialog.component.html'
})
export class OrderLinesDeleteDialogComponent {
  orderLines: IOrderLines;

  constructor(
    protected orderLinesService: OrderLinesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.orderLinesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'orderLinesListModification',
        content: 'Deleted an orderLines'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-order-lines-delete-popup',
  template: ''
})
export class OrderLinesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ orderLines }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OrderLinesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.orderLines = orderLines;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/order-lines', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/order-lines', { outlets: { popup: null } }]);
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
