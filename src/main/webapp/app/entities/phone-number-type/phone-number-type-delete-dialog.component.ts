import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhoneNumberType } from 'app/shared/model/phone-number-type.model';
import { PhoneNumberTypeService } from './phone-number-type.service';

@Component({
  selector: 'jhi-phone-number-type-delete-dialog',
  templateUrl: './phone-number-type-delete-dialog.component.html'
})
export class PhoneNumberTypeDeleteDialogComponent {
  phoneNumberType: IPhoneNumberType;

  constructor(
    protected phoneNumberTypeService: PhoneNumberTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.phoneNumberTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'phoneNumberTypeListModification',
        content: 'Deleted an phoneNumberType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-phone-number-type-delete-popup',
  template: ''
})
export class PhoneNumberTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ phoneNumberType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PhoneNumberTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.phoneNumberType = phoneNumberType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/phone-number-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/phone-number-type', { outlets: { popup: null } }]);
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
