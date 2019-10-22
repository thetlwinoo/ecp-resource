export interface ICompareProducts {
  id?: number;
  productProductName?: string;
  productId?: number;
  compareId?: number;
}

export class CompareProducts implements ICompareProducts {
  constructor(public id?: number, public productProductName?: string, public productId?: number, public compareId?: number) {}
}
