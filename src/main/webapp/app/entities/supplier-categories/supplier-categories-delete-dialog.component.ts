import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';
import { SupplierCategoriesService } from './supplier-categories.service';

@Component({
  selector: 'jhi-supplier-categories-delete-dialog',
  templateUrl: './supplier-categories-delete-dialog.component.html'
})
export class SupplierCategoriesDeleteDialogComponent {
  supplierCategories: ISupplierCategories;

  constructor(
    protected supplierCategoriesService: SupplierCategoriesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.supplierCategoriesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'supplierCategoriesListModification',
        content: 'Deleted an supplierCategories'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-supplier-categories-delete-popup',
  template: ''
})
export class SupplierCategoriesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ supplierCategories }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SupplierCategoriesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.supplierCategories = supplierCategories;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/supplier-categories', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/supplier-categories', { outlets: { popup: null } }]);
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
