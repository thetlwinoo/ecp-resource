export interface IProductOptionSet {
  id?: number;
  productOptionSetValue?: string;
}

export class ProductOptionSet implements IProductOptionSet {
  constructor(public id?: number, public productOptionSetValue?: string) {}
}
