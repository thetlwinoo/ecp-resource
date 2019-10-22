import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBuyingGroups } from 'app/shared/model/buying-groups.model';
import { BuyingGroupsService } from './buying-groups.service';

@Component({
  selector: 'jhi-buying-groups-delete-dialog',
  templateUrl: './buying-groups-delete-dialog.component.html'
})
export class BuyingGroupsDeleteDialogComponent {
  buyingGroups: IBuyingGroups;

  constructor(
    protected buyingGroupsService: BuyingGroupsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.buyingGroupsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'buyingGroupsListModification',
        content: 'Deleted an buyingGroups'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-buying-groups-delete-popup',
  template: ''
})
export class BuyingGroupsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ buyingGroups }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BuyingGroupsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.buyingGroups = buyingGroups;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/buying-groups', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/buying-groups', { outlets: { popup: null } }]);
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
