import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICities } from 'app/shared/model/cities.model';
import { CitiesService } from './cities.service';

@Component({
  selector: 'jhi-cities-delete-dialog',
  templateUrl: './cities-delete-dialog.component.html'
})
export class CitiesDeleteDialogComponent {
  cities: ICities;

  constructor(protected citiesService: CitiesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.citiesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'citiesListModification',
        content: 'Deleted an cities'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cities-delete-popup',
  template: ''
})
export class CitiesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cities }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CitiesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cities = cities;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cities', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cities', { outlets: { popup: null } }]);
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
