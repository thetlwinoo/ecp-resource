export interface ICustomers {
  id?: number;
  accountNumber?: string;
  personFullName?: string;
  personId?: number;
}

export class Customers implements ICustomers {
  constructor(public id?: number, public accountNumber?: string, public personFullName?: string, public personId?: number) {}
}
