import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';
import { ShoppingCartItemsService } from './shopping-cart-items.service';

@Component({
  selector: 'jhi-shopping-cart-items-delete-dialog',
  templateUrl: './shopping-cart-items-delete-dialog.component.html'
})
export class ShoppingCartItemsDeleteDialogComponent {
  shoppingCartItems: IShoppingCartItems;

  constructor(
    protected shoppingCartItemsService: ShoppingCartItemsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.shoppingCartItemsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'shoppingCartItemsListModification',
        content: 'Deleted an shoppingCartItems'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-shopping-cart-items-delete-popup',
  template: ''
})
export class ShoppingCartItemsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ shoppingCartItems }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ShoppingCartItemsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.shoppingCartItems = shoppingCartItems;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/shopping-cart-items', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/shopping-cart-items', { outlets: { popup: null } }]);
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
