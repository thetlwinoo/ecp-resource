import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhotos } from 'app/shared/model/photos.model';
import { PhotosService } from './photos.service';

@Component({
  selector: 'jhi-photos-delete-dialog',
  templateUrl: './photos-delete-dialog.component.html'
})
export class PhotosDeleteDialogComponent {
  photos: IPhotos;

  constructor(protected photosService: PhotosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.photosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'photosListModification',
        content: 'Deleted an photos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-photos-delete-popup',
  template: ''
})
export class PhotosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ photos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PhotosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.photos = photos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/photos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/photos', { outlets: { popup: null } }]);
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
