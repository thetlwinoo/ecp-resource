export interface IProductCatalog {
  id?: number;
  productCategoryName?: string;
  productCategoryId?: number;
  productProductName?: string;
  productId?: number;
}

export class ProductCatalog implements IProductCatalog {
  constructor(
    public id?: number,
    public productCategoryName?: string,
    public productCategoryId?: number,
    public productProductName?: string,
    public productId?: number
  ) {}
}
