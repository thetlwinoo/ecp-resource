export interface IAddresses {
  id?: number;
  contactPerson?: string;
  contactNumber?: string;
  contactEmailAddress?: string;
  addressLine1?: string;
  addressLine2?: string;
  city?: string;
  postalCode?: string;
  defaultInd?: boolean;
  activeInd?: boolean;
  stateProvinceStateProvinceName?: string;
  stateProvinceId?: number;
  addressTypeAddressTypeName?: string;
  addressTypeId?: number;
  personId?: number;
}

export class Addresses implements IAddresses {
  constructor(
    public id?: number,
    public contactPerson?: string,
    public contactNumber?: string,
    public contactEmailAddress?: string,
    public addressLine1?: string,
    public addressLine2?: string,
    public city?: string,
    public postalCode?: string,
    public defaultInd?: boolean,
    public activeInd?: boolean,
    public stateProvinceStateProvinceName?: string,
    public stateProvinceId?: number,
    public addressTypeAddressTypeName?: string,
    public addressTypeId?: number,
    public personId?: number
  ) {
    this.defaultInd = this.defaultInd || false;
    this.activeInd = this.activeInd || false;
  }
}
