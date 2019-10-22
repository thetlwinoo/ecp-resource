export interface IProductSet {
  id?: number;
  productSetName?: string;
  noOfPerson?: number;
  isExclusive?: boolean;
}

export class ProductSet implements IProductSet {
  constructor(public id?: number, public productSetName?: string, public noOfPerson?: number, public isExclusive?: boolean) {
    this.isExclusive = this.isExclusive || false;
  }
}
