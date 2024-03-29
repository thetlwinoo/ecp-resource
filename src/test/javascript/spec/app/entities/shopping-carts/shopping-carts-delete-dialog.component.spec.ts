import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceTestModule } from '../../../test.module';
import { ShoppingCartsDeleteDialogComponent } from 'app/entities/shopping-carts/shopping-carts-delete-dialog.component';
import { ShoppingCartsService } from 'app/entities/shopping-carts/shopping-carts.service';

describe('Component Tests', () => {
  describe('ShoppingCarts Management Delete Component', () => {
    let comp: ShoppingCartsDeleteDialogComponent;
    let fixture: ComponentFixture<ShoppingCartsDeleteDialogComponent>;
    let service: ShoppingCartsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResourceTestModule],
        declarations: [ShoppingCartsDeleteDialogComponent]
      })
        .overrideTemplate(ShoppingCartsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShoppingCartsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShoppingCartsService);
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
