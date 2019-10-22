import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpecialDeals } from 'app/shared/model/special-deals.model';
import { SpecialDealsService } from './special-deals.service';

@Component({
  selector: 'jhi-special-deals-delete-dialog',
  templateUrl: './special-deals-delete-dialog.component.html'
})
export class SpecialDealsDeleteDialogComponent {
  specialDeals: ISpecialDeals;

  constructor(
    protected specialDealsService: SpecialDealsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.specialDealsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'specialDealsListModification',
        content: 'Deleted an specialDeals'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-special-deals-delete-popup',
  template: ''
})
export class SpecialDealsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ specialDeals }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SpecialDealsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.specialDeals = specialDeals;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/special-deals', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/special-deals', { outlets: { popup: null } }]);
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
