import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStateProvinces } from 'app/shared/model/state-provinces.model';
import { StateProvincesService } from './state-provinces.service';

@Component({
  selector: 'jhi-state-provinces-delete-dialog',
  templateUrl: './state-provinces-delete-dialog.component.html'
})
export class StateProvincesDeleteDialogComponent {
  stateProvinces: IStateProvinces;

  constructor(
    protected stateProvincesService: StateProvincesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.stateProvincesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'stateProvincesListModification',
        content: 'Deleted an stateProvinces'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-state-provinces-delete-popup',
  template: ''
})
export class StateProvincesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ stateProvinces }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(StateProvincesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.stateProvinces = stateProvinces;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/state-provinces', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/state-provinces', { outlets: { popup: null } }]);
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
