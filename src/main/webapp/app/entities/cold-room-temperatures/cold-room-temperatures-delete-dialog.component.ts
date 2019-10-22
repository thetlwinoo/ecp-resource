import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';
import { ColdRoomTemperaturesService } from './cold-room-temperatures.service';

@Component({
  selector: 'jhi-cold-room-temperatures-delete-dialog',
  templateUrl: './cold-room-temperatures-delete-dialog.component.html'
})
export class ColdRoomTemperaturesDeleteDialogComponent {
  coldRoomTemperatures: IColdRoomTemperatures;

  constructor(
    protected coldRoomTemperaturesService: ColdRoomTemperaturesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.coldRoomTemperaturesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'coldRoomTemperaturesListModification',
        content: 'Deleted an coldRoomTemperatures'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cold-room-temperatures-delete-popup',
  template: ''
})
export class ColdRoomTemperaturesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ coldRoomTemperatures }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ColdRoomTemperaturesDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.coldRoomTemperatures = coldRoomTemperatures;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cold-room-temperatures', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cold-room-temperatures', { outlets: { popup: null } }]);
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
