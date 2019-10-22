import { Moment } from 'moment';

export interface IStateProvinces {
  id?: number;
  stateProvinceCode?: string;
  stateProvinceName?: string;
  salesTerritory?: string;
  border?: string;
  latestRecordedPopulation?: number;
  validFrom?: Moment;
  validTo?: Moment;
  countryCountryName?: string;
  countryId?: number;
}

export class StateProvinces implements IStateProvinces {
  constructor(
    public id?: number,
    public stateProvinceCode?: string,
    public stateProvinceName?: string,
    public salesTerritory?: string,
    public border?: string,
    public latestRecordedPopulation?: number,
    public validFrom?: Moment,
    public validTo?: Moment,
    public countryCountryName?: string,
    public countryId?: number
  ) {}
}
