export interface ICulture {
  id?: number;
  cultureCode?: string;
  cultureName?: string;
}

export class Culture implements ICulture {
  constructor(public id?: number, public cultureCode?: string, public cultureName?: string) {}
}
