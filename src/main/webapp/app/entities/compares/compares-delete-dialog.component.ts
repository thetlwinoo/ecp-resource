import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompares } from 'app/shared/model/compares.model';
import { ComparesService } from './compares.service';

@Component({
  selector: 'jhi-compares-delete-dialog',
  templateUrl: './compares-delete-dialog.component.html'
})
export class ComparesDeleteDialogComponent {
  compares: ICompares;

  constructor(protected comparesService: ComparesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.comparesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'comparesListModification',
        content: 'Deleted an compares'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compares-delete-popup',
  template: ''
})
export class ComparesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compares }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ComparesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.compares = compares;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compares', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compares', { outlets: { popup: null } }]);
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
