import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentMethods } from 'app/shared/model/payment-methods.model';

@Component({
  selector: 'jhi-payment-methods-detail',
  templateUrl: './payment-methods-detail.component.html'
})
export class PaymentMethodsDetailComponent implements OnInit {
  paymentMethods: IPaymentMethods;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paymentMethods }) => {
      this.paymentMethods = paymentMethods;
    });
  }

  previousState() {
    window.history.back();
  }
}
