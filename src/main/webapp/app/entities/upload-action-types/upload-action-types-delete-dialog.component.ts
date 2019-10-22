import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { UploadActionTypesService } from './upload-action-types.service';

@Component({
  selector: 'jhi-upload-action-types-delete-dialog',
  templateUrl: './upload-action-types-delete-dialog.component.html'
})
export class UploadActionTypesDeleteDialogComponent {
  uploadActionTypes: IUploadActionTypes;

  constructor(
    protected uploadActionTypesService: UploadActionTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.uploadActionTypesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'uploadActionTypesListModification',
        content: 'Deleted an uploadActionTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-upload-action-types-delete-popup',
  template: ''
})
export class UploadActionTypesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ uploadActionTypes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UploadActionTypesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.uploadActionTypes = uploadActionTypes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/upload-action-types', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/upload-action-types', { outlets: { popup: null } }]);
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
