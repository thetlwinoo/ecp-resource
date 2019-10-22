export interface IAddressTypes {
  id?: number;
  addressTypeName?: string;
  refer?: string;
}

export class AddressTypes implements IAddressTypes {
  constructor(public id?: number, public addressTypeName?: string, public refer?: string) {}
}
