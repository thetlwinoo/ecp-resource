import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMaterials } from 'app/shared/model/materials.model';
import { MaterialsService } from './materials.service';

@Component({
  selector: 'jhi-materials-delete-dialog',
  templateUrl: './materials-delete-dialog.component.html'
})
export class MaterialsDeleteDialogComponent {
  materials: IMaterials;

  constructor(protected materialsService: MaterialsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.materialsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'materialsListModification',
        content: 'Deleted an materials'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-materials-delete-popup',
  template: ''
})
export class MaterialsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ materials }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MaterialsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.materials = materials;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/materials', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/materials', { outlets: { popup: null } }]);
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
