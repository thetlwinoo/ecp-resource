import { Moment } from 'moment';

export interface IBuyingGroups {
  id?: number;
  buyingGroupName?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class BuyingGroups implements IBuyingGroups {
  constructor(public id?: number, public buyingGroupName?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
