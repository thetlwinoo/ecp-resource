import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDangerousGoods } from 'app/shared/model/dangerous-goods.model';
import { DangerousGoodsService } from './dangerous-goods.service';

@Component({
  selector: 'jhi-dangerous-goods-delete-dialog',
  templateUrl: './dangerous-goods-delete-dialog.component.html'
})
export class DangerousGoodsDeleteDialogComponent {
  dangerousGoods: IDangerousGoods;

  constructor(
    protected dangerousGoodsService: DangerousGoodsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dangerousGoodsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dangerousGoodsListModification',
        content: 'Deleted an dangerousGoods'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-dangerous-goods-delete-popup',
  template: ''
})
export class DangerousGoodsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dangerousGoods }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DangerousGoodsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dangerousGoods = dangerousGoods;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/dangerous-goods', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/dangerous-goods', { outlets: { popup: null } }]);
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
