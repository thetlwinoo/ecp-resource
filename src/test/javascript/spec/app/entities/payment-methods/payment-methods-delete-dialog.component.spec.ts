import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceTestModule } from '../../../test.module';
import { PaymentMethodsDeleteDialogComponent } from 'app/entities/payment-methods/payment-methods-delete-dialog.component';
import { PaymentMethodsService } from 'app/entities/payment-methods/payment-methods.service';

describe('Component Tests', () => {
  describe('PaymentMethods Management Delete Component', () => {
    let comp: PaymentMethodsDeleteDialogComponent;
    let fixture: ComponentFixture<PaymentMethodsDeleteDialogComponent>;
    let service: PaymentMethodsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResourceTestModule],
        declarations: [PaymentMethodsDeleteDialogComponent]
      })
        .overrideTemplate(PaymentMethodsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentMethodsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentMethodsService);
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
