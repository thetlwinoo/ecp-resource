import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceTestModule } from '../../../test.module';
import { ProductTagsDeleteDialogComponent } from 'app/entities/product-tags/product-tags-delete-dialog.component';
import { ProductTagsService } from 'app/entities/product-tags/product-tags.service';

describe('Component Tests', () => {
  describe('ProductTags Management Delete Component', () => {
    let comp: ProductTagsDeleteDialogComponent;
    let fixture: ComponentFixture<ProductTagsDeleteDialogComponent>;
    let service: ProductTagsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResourceTestModule],
        declarations: [ProductTagsDeleteDialogComponent]
      })
        .overrideTemplate(ProductTagsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductTagsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductTagsService);
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
