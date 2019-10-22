import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessEntityAddress } from 'app/shared/model/business-entity-address.model';
import { BusinessEntityAddressService } from './business-entity-address.service';

@Component({
  selector: 'jhi-business-entity-address-delete-dialog',
  templateUrl: './business-entity-address-delete-dialog.component.html'
})
export class BusinessEntityAddressDeleteDialogComponent {
  businessEntityAddress: IBusinessEntityAddress;

  constructor(
    protected businessEntityAddressService: BusinessEntityAddressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.businessEntityAddressService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'businessEntityAddressListModification',
        content: 'Deleted an businessEntityAddress'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-business-entity-address-delete-popup',
  template: ''
})
export class BusinessEntityAddressDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ businessEntityAddress }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BusinessEntityAddressDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.businessEntityAddress = businessEntityAddress;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/business-entity-address', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/business-entity-address', { outlets: { popup: null } }]);
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
