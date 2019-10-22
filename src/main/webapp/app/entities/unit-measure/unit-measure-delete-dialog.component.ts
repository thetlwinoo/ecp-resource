import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUnitMeasure } from 'app/shared/model/unit-measure.model';
import { UnitMeasureService } from './unit-measure.service';

@Component({
  selector: 'jhi-unit-measure-delete-dialog',
  templateUrl: './unit-measure-delete-dialog.component.html'
})
export class UnitMeasureDeleteDialogComponent {
  unitMeasure: IUnitMeasure;

  constructor(
    protected unitMeasureService: UnitMeasureService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.unitMeasureService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'unitMeasureListModification',
        content: 'Deleted an unitMeasure'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-unit-measure-delete-popup',
  template: ''
})
export class UnitMeasureDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ unitMeasure }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UnitMeasureDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.unitMeasure = unitMeasure;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/unit-measure', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/unit-measure', { outlets: { popup: null } }]);
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
