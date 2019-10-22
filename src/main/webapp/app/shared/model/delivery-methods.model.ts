import { Moment } from 'moment';

export interface IDeliveryMethods {
  id?: number;
  deliveryMethodName?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class DeliveryMethods implements IDeliveryMethods {
  constructor(public id?: number, public deliveryMethodName?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
