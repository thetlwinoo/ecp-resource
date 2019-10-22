import { Moment } from 'moment';

export interface IPackageTypes {
  id?: number;
  packageTypeName?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class PackageTypes implements IPackageTypes {
  constructor(public id?: number, public packageTypeName?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
