import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from './wishlists.service';

@Component({
  selector: 'jhi-wishlists-delete-dialog',
  templateUrl: './wishlists-delete-dialog.component.html'
})
export class WishlistsDeleteDialogComponent {
  wishlists: IWishlists;

  constructor(protected wishlistsService: WishlistsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.wishlistsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'wishlistsListModification',
        content: 'Deleted an wishlists'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-wishlists-delete-popup',
  template: ''
})
export class WishlistsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ wishlists }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(WishlistsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.wishlists = wishlists;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/wishlists', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/wishlists', { outlets: { popup: null } }]);
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
