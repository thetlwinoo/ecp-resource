import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItemTransactions } from 'app/shared/model/stock-item-transactions.model';
import { StockItemTransactionsService } from './stock-item-transactions.service';

@Component({
  selector: 'jhi-stock-item-transactions-delete-dialog',
  templateUrl: './stock-item-transactions-delete-dialog.component.html'
})
export class StockItemTransactionsDeleteDialogComponent {
  stockItemTransactions: IStockItemTransactions;

  constructor(
    protected stockItemTransactionsService: StockItemTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockItemTransactionsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stockItemTransactionsListModification',
        content: 'Deleted an stockItemTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-stock-item-transactions-delete-popup',
  template: ''
})
export class StockItemTransactionsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockItemTransactions }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StockItemTransactionsDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.stockItemTransactions = stockItemTransactions;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/stock-item-transactions', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/stock-item-transactions', { outlets: { popup: null } }]);
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
