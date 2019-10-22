import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from './transaction-types.service';

@Component({
  selector: 'jhi-transaction-types-delete-dialog',
  templateUrl: './transaction-types-delete-dialog.component.html'
})
export class TransactionTypesDeleteDialogComponent {
  transactionTypes: ITransactionTypes;

  constructor(
    protected transactionTypesService: TransactionTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.transactionTypesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'transactionTypesListModification',
        content: 'Deleted an transactionTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-transaction-types-delete-popup',
  template: ''
})
export class TransactionTypesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ transactionTypes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TransactionTypesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.transactionTypes = transactionTypes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/transaction-types', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/transaction-types', { outlets: { popup: null } }]);
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
