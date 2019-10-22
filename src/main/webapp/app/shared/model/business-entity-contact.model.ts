export interface IBusinessEntityContact {
  id?: number;
  personId?: number;
  contactTypeContactTypeName?: string;
  contactTypeId?: number;
}

export class BusinessEntityContact implements IBusinessEntityContact {
  constructor(public id?: number, public personId?: number, public contactTypeContactTypeName?: string, public contactTypeId?: number) {}
}
