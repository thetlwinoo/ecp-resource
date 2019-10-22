import { Moment } from 'moment';

export interface IPaymentMethods {
  id?: number;
  paymentMethodName?: string;
  activeInd?: boolean;
  validFrom?: Moment;
  validTo?: Moment;
}

export class PaymentMethods implements IPaymentMethods {
  constructor(
    public id?: number,
    public paymentMethodName?: string,
    public activeInd?: boolean,
    public validFrom?: Moment,
    public validTo?: Moment
  ) {
    this.activeInd = this.activeInd || false;
  }
}
