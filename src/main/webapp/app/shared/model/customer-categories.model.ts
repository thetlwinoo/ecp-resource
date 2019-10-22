import { Moment } from 'moment';

export interface ICustomerCategories {
  id?: number;
  customerCategoryName?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class CustomerCategories implements ICustomerCategories {
  constructor(public id?: number, public customerCategoryName?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
