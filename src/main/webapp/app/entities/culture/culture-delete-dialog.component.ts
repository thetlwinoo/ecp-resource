import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICulture } from 'app/shared/model/culture.model';
import { CultureService } from './culture.service';

@Component({
  selector: 'jhi-culture-delete-dialog',
  templateUrl: './culture-delete-dialog.component.html'
})
export class CultureDeleteDialogComponent {
  culture: ICulture;

  constructor(protected cultureService: CultureService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cultureService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cultureListModification',
        content: 'Deleted an culture'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-culture-delete-popup',
  template: ''
})
export class CultureDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ culture }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CultureDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.culture = culture;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/culture', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/culture', { outlets: { popup: null } }]);
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
