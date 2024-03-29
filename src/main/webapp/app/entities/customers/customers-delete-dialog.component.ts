import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomers } from 'app/shared/model/customers.model';
import { CustomersService } from './customers.service';

@Component({
  selector: 'jhi-customers-delete-dialog',
  templateUrl: './customers-delete-dialog.component.html'
})
export class CustomersDeleteDialogComponent {
  customers: ICustomers;

  constructor(protected customersService: CustomersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customersService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'customersListModification',
        content: 'Deleted an customers'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-customers-delete-popup',
  template: ''
})
export class CustomersDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customers }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CustomersDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.customers = customers;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/customers', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/customers', { outlets: { popup: null } }]);
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
