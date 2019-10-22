import { Moment } from 'moment';

export interface ITransactionTypes {
  id?: number;
  transactionTypeName?: string;
  validFrom?: Moment;
  validTo?: Moment;
}

export class TransactionTypes implements ITransactionTypes {
  constructor(public id?: number, public transactionTypeName?: string, public validFrom?: Moment, public validTo?: Moment) {}
}
