import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';
import { WarrantyTypesService } from './warranty-types.service';

@Component({
  selector: 'jhi-warranty-types-delete-dialog',
  templateUrl: './warranty-types-delete-dialog.component.html'
})
export class WarrantyTypesDeleteDialogComponent {
  warrantyTypes: IWarrantyTypes;

  constructor(
    protected warrantyTypesService: WarrantyTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.warrantyTypesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'warrantyTypesListModification',
        content: 'Deleted an warrantyTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-warranty-types-delete-popup',
  template: ''
})
export class WarrantyTypesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ warrantyTypes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(WarrantyTypesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.warrantyTypes = warrantyTypes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/warranty-types', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/warranty-types', { outlets: { popup: null } }]);
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
