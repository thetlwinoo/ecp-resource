export interface IProductBrand {
  id?: number;
  productBrandName?: string;
  photoContentType?: string;
  photo?: any;
}

export class ProductBrand implements IProductBrand {
  constructor(public id?: number, public productBrandName?: string, public photoContentType?: string, public photo?: any) {}
}
