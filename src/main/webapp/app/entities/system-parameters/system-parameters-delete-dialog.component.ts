import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISystemParameters } from 'app/shared/model/system-parameters.model';
import { SystemParametersService } from './system-parameters.service';

@Component({
  selector: 'jhi-system-parameters-delete-dialog',
  templateUrl: './system-parameters-delete-dialog.component.html'
})
export class SystemParametersDeleteDialogComponent {
  systemParameters: ISystemParameters;

  constructor(
    protected systemParametersService: SystemParametersService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.systemParametersService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'systemParametersListModification',
        content: 'Deleted an systemParameters'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-system-parameters-delete-popup',
  template: ''
})
export class SystemParametersDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ systemParameters }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SystemParametersDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.systemParameters = systemParameters;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/system-parameters', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/system-parameters', { outlets: { popup: null } }]);
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
