import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItemHoldings } from 'app/shared/model/stock-item-holdings.model';
import { StockItemHoldingsService } from './stock-item-holdings.service';

@Component({
  selector: 'jhi-stock-item-holdings-delete-dialog',
  templateUrl: './stock-item-holdings-delete-dialog.component.html'
})
export class StockItemHoldingsDeleteDialogComponent {
  stockItemHoldings: IStockItemHoldings;

  constructor(
    protected stockItemHoldingsService: StockItemHoldingsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockItemHoldingsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stockItemHoldingsListModification',
        content: 'Deleted an stockItemHoldings'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-stock-item-holdings-delete-popup',
  template: ''
})
export class StockItemHoldingsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockItemHoldings }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StockItemHoldingsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.stockItemHoldings = stockItemHoldings;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/stock-item-holdings', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/stock-item-holdings', { outlets: { popup: null } }]);
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
