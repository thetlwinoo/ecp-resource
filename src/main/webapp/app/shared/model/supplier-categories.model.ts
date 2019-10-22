import { Moment } from 'moment';

export interface ISupplierCategories {
  id?: number;
  supplierCategoryName?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class SupplierCategories implements ISupplierCategories {
  constructor(public id?: number, public supplierCategoryName?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
