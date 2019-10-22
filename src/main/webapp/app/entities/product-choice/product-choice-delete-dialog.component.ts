import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductChoice } from 'app/shared/model/product-choice.model';
import { ProductChoiceService } from './product-choice.service';

@Component({
  selector: 'jhi-product-choice-delete-dialog',
  templateUrl: './product-choice-delete-dialog.component.html'
})
export class ProductChoiceDeleteDialogComponent {
  productChoice: IProductChoice;

  constructor(
    protected productChoiceService: ProductChoiceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productChoiceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productChoiceListModification',
        content: 'Deleted an productChoice'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-choice-delete-popup',
  template: ''
})
export class ProductChoiceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productChoice }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductChoiceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productChoice = productChoice;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-choice', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-choice', { outlets: { popup: null } }]);
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
