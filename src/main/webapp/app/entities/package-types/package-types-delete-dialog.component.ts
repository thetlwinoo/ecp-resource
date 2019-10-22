import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPackageTypes } from 'app/shared/model/package-types.model';
import { PackageTypesService } from './package-types.service';

@Component({
  selector: 'jhi-package-types-delete-dialog',
  templateUrl: './package-types-delete-dialog.component.html'
})
export class PackageTypesDeleteDialogComponent {
  packageTypes: IPackageTypes;

  constructor(
    protected packageTypesService: PackageTypesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.packageTypesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'packageTypesListModification',
        content: 'Deleted an packageTypes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-package-types-delete-popup',
  template: ''
})
export class PackageTypesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ packageTypes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PackageTypesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.packageTypes = packageTypes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/package-types', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/package-types', { outlets: { popup: null } }]);
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
