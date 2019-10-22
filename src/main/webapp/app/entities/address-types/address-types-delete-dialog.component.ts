import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAddressTypes } from 'app/shared/model/address-types.model';
import { AddressTypesService } from './address-types.service';

@Component({
  selector: 'jhi-address-types-delete-dialog',
  templateUrl: './address-types-delete-dialog.component.html'
})
export class AddressTypesDeleteDialogComponent {
  addressTypes: IAddressTypes;

  constructor(
    protected addressTypesService: AddressTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.addressTypesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'addressTypesListModification',
        content: 'Deleted an addressTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-address-types-delete-popup',
  template: ''
})
export class AddressTypesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ addressTypes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AddressTypesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.addressTypes = addressTypes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/address-types', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/address-types', { outlets: { popup: null } }]);
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
