import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { PaymentMethodsUpdateComponent } from 'app/entities/payment-methods/payment-methods-update.component';
import { PaymentMethodsService } from 'app/entities/payment-methods/payment-methods.service';
import { PaymentMethods } from 'app/shared/model/payment-methods.model';

describe('Component Tests', () => {
  describe('PaymentMethods Management Update Component', () => {
    let comp: PaymentMethodsUpdateComponent;
    let fixture: ComponentFixture<PaymentMethodsUpdateComponent>;
    let service: PaymentMethodsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResourceTestModule],
        declarations: [PaymentMethodsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PaymentMethodsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentMethodsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentMethodsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentMethods(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentMethods();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
