import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ResourceTestModule } from '../../../test.module';
import { PaymentMethodsDetailComponent } from 'app/entities/payment-methods/payment-methods-detail.component';
import { PaymentMethods } from 'app/shared/model/payment-methods.model';

describe('Component Tests', () => {
  describe('PaymentMethods Management Detail Component', () => {
    let comp: PaymentMethodsDetailComponent;
    let fixture: ComponentFixture<PaymentMethodsDetailComponent>;
    const route = ({ data: of({ paymentMethods: new PaymentMethods(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ResourceTestModule],
        declarations: [PaymentMethodsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaymentMethodsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentMethodsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentMethods).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
