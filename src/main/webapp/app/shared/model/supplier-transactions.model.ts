import { Moment } from 'moment';

export interface ISupplierTransactions {
  id?: number;
  supplierInvoiceNumber?: string;
  transactionDate?: Moment;
  amountExcludingTax?: number;
  taxAmount?: number;
  transactionAmount?: number;
  outstandingBalance?: number;
  finalizationDate?: Moment;
  isFinalized?: boolean;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  supplierSupplierName?: string;
  supplierId?: number;
  transactionTypeTransactionTypeName?: string;
  transactionTypeId?: number;
  purchaseOrderId?: number;
  paymentMethodPaymentMethodName?: string;
  paymentMethodId?: number;
}

export class SupplierTransactions implements ISupplierTransactions {
  constructor(
    public id?: number,
    public supplierInvoiceNumber?: string,
    public transactionDate?: Moment,
    public amountExcludingTax?: number,
    public taxAmount?: number,
    public transactionAmount?: number,
    public outstandingBalance?: number,
    public finalizationDate?: Moment,
    public isFinalized?: boolean,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public supplierSupplierName?: string,
    public supplierId?: number,
    public transactionTypeTransactionTypeName?: string,
    public transactionTypeId?: number,
    public purchaseOrderId?: number,
    public paymentMethodPaymentMethodName?: string,
    public paymentMethodId?: number
  ) {
    this.isFinalized = this.isFinalized || false;
  }
}
