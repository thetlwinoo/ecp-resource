import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContactType } from 'app/shared/model/contact-type.model';
import { ContactTypeService } from './contact-type.service';

@Component({
  selector: 'jhi-contact-type-delete-dialog',
  templateUrl: './contact-type-delete-dialog.component.html'
})
export class ContactTypeDeleteDialogComponent {
  contactType: IContactType;

  constructor(
    protected contactTypeService: ContactTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.contactTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'contactTypeListModification',
        content: 'Deleted an contactType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-contact-type-delete-popup',
  template: ''
})
export class ContactTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contactType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ContactTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.contactType = contactType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/contact-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/contact-type', { outlets: { popup: null } }]);
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
