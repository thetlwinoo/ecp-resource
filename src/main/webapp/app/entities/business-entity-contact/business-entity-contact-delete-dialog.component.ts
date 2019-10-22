import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessEntityContact } from 'app/shared/model/business-entity-contact.model';
import { BusinessEntityContactService } from './business-entity-contact.service';

@Component({
  selector: 'jhi-business-entity-contact-delete-dialog',
  templateUrl: './business-entity-contact-delete-dialog.component.html'
})
export class BusinessEntityContactDeleteDialogComponent {
  businessEntityContact: IBusinessEntityContact;

  constructor(
    protected businessEntityContactService: BusinessEntityContactService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.businessEntityContactService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'businessEntityContactListModification',
        content: 'Deleted an businessEntityContact'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-business-entity-contact-delete-popup',
  template: ''
})
export class BusinessEntityContactDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ businessEntityContact }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BusinessEntityContactDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.businessEntityContact = businessEntityContact;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/business-entity-contact', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/business-entity-contact', { outlets: { popup: null } }]);
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
