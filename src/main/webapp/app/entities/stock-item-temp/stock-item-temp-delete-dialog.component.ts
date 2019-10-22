import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItemTemp } from 'app/shared/model/stock-item-temp.model';
import { StockItemTempService } from './stock-item-temp.service';

@Component({
  selector: 'jhi-stock-item-temp-delete-dialog',
  templateUrl: './stock-item-temp-delete-dialog.component.html'
})
export class StockItemTempDeleteDialogComponent {
  stockItemTemp: IStockItemTemp;

  constructor(
    protected stockItemTempService: StockItemTempService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockItemTempService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stockItemTempListModification',
        content: 'Deleted an stockItemTemp'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-stock-item-temp-delete-popup',
  template: ''
})
export class StockItemTempDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockItemTemp }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StockItemTempDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.stockItemTemp = stockItemTemp;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/stock-item-temp', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/stock-item-temp', { outlets: { popup: null } }]);
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
