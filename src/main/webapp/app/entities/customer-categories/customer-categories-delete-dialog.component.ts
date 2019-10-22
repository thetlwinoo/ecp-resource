import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICustomerCategories } from 'app/shared/model/customer-categories.model';
import { CustomerCategoriesService } from './customer-categories.service';

@Component({
  selector: 'jhi-customer-categories-delete-dialog',
  templateUrl: './customer-categories-delete-dialog.component.html'
})
export class CustomerCategoriesDeleteDialogComponent {
  customerCategories: ICustomerCategories;

  constructor(
    protected customerCategoriesService: CustomerCategoriesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.customerCategoriesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'customerCategoriesListModification',
        content: 'Deleted an customerCategories'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-customer-categories-delete-popup',
  template: ''
})
export class CustomerCategoriesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ customerCategories }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CustomerCategoriesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.customerCategories = customerCategories;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/customer-categories', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/customer-categories', { outlets: { popup: null } }]);
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
