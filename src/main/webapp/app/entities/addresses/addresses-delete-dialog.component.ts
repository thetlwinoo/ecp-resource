import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAddresses } from 'app/shared/model/addresses.model';
import { AddressesService } from './addresses.service';

@Component({
  selector: 'jhi-addresses-delete-dialog',
  templateUrl: './addresses-delete-dialog.component.html'
})
export class AddressesDeleteDialogComponent {
  addresses: IAddresses;

  constructor(protected addressesService: AddressesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.addressesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'addressesListModification',
        content: 'Deleted an addresses'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-addresses-delete-popup',
  template: ''
})
export class AddressesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ addresses }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AddressesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.addresses = addresses;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/addresses', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/addresses', { outlets: { popup: null } }]);
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
