export interface IProductAttributeSet {
  id?: number;
  productAttributeSetName?: string;
  productOptionSetProductOptionSetValue?: string;
  productOptionSetId?: number;
}

export class ProductAttributeSet implements IProductAttributeSet {
  constructor(
    public id?: number,
    public productAttributeSetName?: string,
    public productOptionSetProductOptionSetValue?: string,
    public productOptionSetId?: number
  ) {}
}
