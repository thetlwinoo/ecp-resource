export interface IPhoneNumberType {
  id?: number;
  phoneNumberTypeName?: string;
}

export class PhoneNumberType implements IPhoneNumberType {
  constructor(public id?: number, public phoneNumberTypeName?: string) {}
}
