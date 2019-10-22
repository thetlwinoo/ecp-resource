import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShipMethod } from 'app/shared/model/ship-method.model';
import { ShipMethodService } from './ship-method.service';

@Component({
  selector: 'jhi-ship-method-delete-dialog',
  templateUrl: './ship-method-delete-dialog.component.html'
})
export class ShipMethodDeleteDialogComponent {
  shipMethod: IShipMethod;

  constructor(
    protected shipMethodService: ShipMethodService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shipMethodService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'shipMethodListModification',
        content: 'Deleted an shipMethod'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ship-method-delete-popup',
  template: ''
})
export class ShipMethodDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shipMethod }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ShipMethodDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.shipMethod = shipMethod;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ship-method', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ship-method', { outlets: { popup: null } }]);
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
