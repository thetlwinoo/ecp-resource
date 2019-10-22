export interface ICurrency {
  id?: number;
  currencyCode?: string;
  currencyName?: string;
}

export class Currency implements ICurrency {
  constructor(public id?: number, public currencyCode?: string, public currencyName?: string) {}
}
