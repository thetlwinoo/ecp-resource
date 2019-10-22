import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from './upload-transactions.service';

@Component({
  selector: 'jhi-upload-transactions-delete-dialog',
  templateUrl: './upload-transactions-delete-dialog.component.html'
})
export class UploadTransactionsDeleteDialogComponent {
  uploadTransactions: IUploadTransactions;

  constructor(
    protected uploadTransactionsService: UploadTransactionsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.uploadTransactionsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'uploadTransactionsListModification',
        content: 'Deleted an uploadTransactions'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-upload-transactions-delete-popup',
  template: ''
})
export class UploadTransactionsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ uploadTransactions }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UploadTransactionsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.uploadTransactions = uploadTransactions;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/upload-transactions', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/upload-transactions', { outlets: { popup: null } }]);
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
