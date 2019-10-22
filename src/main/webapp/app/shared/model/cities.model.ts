import { Moment } from 'moment';

export interface ICities {
  id?: number;
  cityName?: string;
  location?: string;
  latestRecordedPopulation?: number;
  validFrom?: Moment;
  validTo?: Moment;
  stateProvinceStateProvinceName?: string;
  stateProvinceId?: number;
}

export class Cities implements ICities {
  constructor(
    public id?: number,
    public cityName?: string,
    public location?: string,
    public latestRecordedPopulation?: number,
    public validFrom?: Moment,
    public validTo?: Moment,
    public stateProvinceStateProvinceName?: string,
    public stateProvinceId?: number
  ) {}
}
