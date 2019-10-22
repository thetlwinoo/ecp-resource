export interface IBusinessEntityAddress {
  id?: number;
  addressId?: number;
  personId?: number;
  addressTypeAddressTypeName?: string;
  addressTypeId?: number;
}

export class BusinessEntityAddress implements IBusinessEntityAddress {
  constructor(
    public id?: number,
    public addressId?: number,
    public personId?: number,
    public addressTypeAddressTypeName?: string,
    public addressTypeId?: number
  ) {}
}
