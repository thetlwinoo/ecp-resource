import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShoppingCarts } from 'app/shared/model/shopping-carts.model';
import { ShoppingCartsService } from './shopping-carts.service';

@Component({
  selector: 'jhi-shopping-carts-delete-dialog',
  templateUrl: './shopping-carts-delete-dialog.component.html'
})
export class ShoppingCartsDeleteDialogComponent {
  shoppingCarts: IShoppingCarts;

  constructor(
    protected shoppingCartsService: ShoppingCartsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shoppingCartsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'shoppingCartsListModification',
        content: 'Deleted an shoppingCarts'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-shopping-carts-delete-popup',
  template: ''
})
export class ShoppingCartsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shoppingCarts }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ShoppingCartsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.shoppingCarts = shoppingCarts;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/shopping-carts', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/shopping-carts', { outlets: { popup: null } }]);
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
