import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceTestModule } from '../../../test.module';
import { BusinessEntityAddressDeleteDialogComponent } from 'app/entities/business-entity-address/business-entity-address-delete-dialog.component';
import { BusinessEntityAddressService } from 'app/entities/business-entity-address/business-entity-address.service';

describe('Component Tests', () => {
  describe('BusinessEntityAddress Management Delete Component', () => {
    let comp: BusinessEntityAddressDeleteDialogComponent;
    let fixture: ComponentFixture<BusinessEntityAddressDeleteDialogComponent>;
    let service: BusinessEntityAddressService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResourceTestModule],
        declarations: [BusinessEntityAddressDeleteDialogComponent]
      })
        .overrideTemplate(BusinessEntityAddressDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessEntityAddressDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessEntityAddressService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
