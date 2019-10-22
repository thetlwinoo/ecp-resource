export interface IProductTags {
  id?: number;
  tagName?: string;
  productProductName?: string;
  productId?: number;
}

export class ProductTags implements IProductTags {
  constructor(public id?: number, public tagName?: string, public productProductName?: string, public productId?: number) {}
}
