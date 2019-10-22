import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockItems } from 'app/shared/model/stock-items.model';
import { StockItemsService } from './stock-items.service';

@Component({
  selector: 'jhi-stock-items-delete-dialog',
  templateUrl: './stock-items-delete-dialog.component.html'
})
export class StockItemsDeleteDialogComponent {
  stockItems: IStockItems;

  constructor(
    protected stockItemsService: StockItemsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stockItemsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stockItemsListModification',
        content: 'Deleted an stockItems'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-stock-items-delete-popup',
  template: ''
})
export class StockItemsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stockItems }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StockItemsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.stockItems = stockItems;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/stock-items', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/stock-items', { outlets: { popup: null } }]);
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
