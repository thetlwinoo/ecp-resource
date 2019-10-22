import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPersonPhone } from 'app/shared/model/person-phone.model';
import { PersonPhoneService } from './person-phone.service';

@Component({
  selector: 'jhi-person-phone-delete-dialog',
  templateUrl: './person-phone-delete-dialog.component.html'
})
export class PersonPhoneDeleteDialogComponent {
  personPhone: IPersonPhone;

  constructor(
    protected personPhoneService: PersonPhoneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.personPhoneService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'personPhoneListModification',
        content: 'Deleted an personPhone'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-person-phone-delete-popup',
  template: ''
})
export class PersonPhoneDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ personPhone }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PersonPhoneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.personPhone = personPhone;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/person-phone', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/person-phone', { outlets: { popup: null } }]);
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
