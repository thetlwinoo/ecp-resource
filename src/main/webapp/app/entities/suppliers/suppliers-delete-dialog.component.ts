import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISuppliers } from 'app/shared/model/suppliers.model';
import { SuppliersService } from './suppliers.service';

@Component({
  selector: 'jhi-suppliers-delete-dialog',
  templateUrl: './suppliers-delete-dialog.component.html'
})
export class SuppliersDeleteDialogComponent {
  suppliers: ISuppliers;

  constructor(protected suppliersService: SuppliersService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.suppliersService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'suppliersListModification',
        content: 'Deleted an suppliers'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-suppliers-delete-popup',
  template: ''
})
export class SuppliersDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ suppliers }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SuppliersDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.suppliers = suppliers;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/suppliers', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/suppliers', { outlets: { popup: null } }]);
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
