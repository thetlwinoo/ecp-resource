export interface IContactType {
  id?: number;
  contactTypeName?: string;
}

export class ContactType implements IContactType {
  constructor(public id?: number, public contactTypeName?: string) {}
}
