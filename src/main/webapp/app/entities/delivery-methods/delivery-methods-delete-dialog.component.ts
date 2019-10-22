import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveryMethods } from 'app/shared/model/delivery-methods.model';
import { DeliveryMethodsService } from './delivery-methods.service';

@Component({
  selector: 'jhi-delivery-methods-delete-dialog',
  templateUrl: './delivery-methods-delete-dialog.component.html'
})
export class DeliveryMethodsDeleteDialogComponent {
  deliveryMethods: IDeliveryMethods;

  constructor(
    protected deliveryMethodsService: DeliveryMethodsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.deliveryMethodsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'deliveryMethodsListModification',
        content: 'Deleted an deliveryMethods'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-delivery-methods-delete-popup',
  template: ''
})
export class DeliveryMethodsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deliveryMethods }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DeliveryMethodsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.deliveryMethods = deliveryMethods;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/delivery-methods', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/delivery-methods', { outlets: { popup: null } }]);
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
