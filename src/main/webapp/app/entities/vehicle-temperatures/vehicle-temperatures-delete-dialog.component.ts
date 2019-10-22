import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';
import { VehicleTemperaturesService } from './vehicle-temperatures.service';

@Component({
  selector: 'jhi-vehicle-temperatures-delete-dialog',
  templateUrl: './vehicle-temperatures-delete-dialog.component.html'
})
export class VehicleTemperaturesDeleteDialogComponent {
  vehicleTemperatures: IVehicleTemperatures;

  constructor(
    protected vehicleTemperaturesService: VehicleTemperaturesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vehicleTemperaturesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vehicleTemperaturesListModification',
        content: 'Deleted an vehicleTemperatures'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vehicle-temperatures-delete-popup',
  template: ''
})
export class VehicleTemperaturesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vehicleTemperatures }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VehicleTemperaturesDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.vehicleTemperatures = vehicleTemperatures;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/vehicle-temperatures', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/vehicle-temperatures', { outlets: { popup: null } }]);
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
