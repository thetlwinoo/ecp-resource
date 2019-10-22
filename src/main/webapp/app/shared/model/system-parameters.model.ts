export interface ISystemParameters {
  id?: number;
  applicationSettings?: string;
  deliveryCityCityName?: string;
  deliveryCityId?: number;
  postalCityCityName?: string;
  postalCityId?: number;
}

export class SystemParameters implements ISystemParameters {
  constructor(
    public id?: number,
    public applicationSettings?: string,
    public deliveryCityCityName?: string,
    public deliveryCityId?: number,
    public postalCityCityName?: string,
    public postalCityId?: number
  ) {}
}
