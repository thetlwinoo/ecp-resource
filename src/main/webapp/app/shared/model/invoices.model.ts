import { Moment } from 'moment';
import { IInvoiceLines } from 'app/shared/model/invoice-lines.model';

export interface IInvoices {
  id?: number;
  invoiceDate?: Moment;
  customerPurchaseOrderNumber?: string;
  isCreditNote?: boolean;
  creditNoteReason?: string;
  comments?: string;
  deliveryInstructions?: string;
  internalComments?: string;
  totalDryItems?: number;
  totalChillerItems?: number;
  deliveryRun?: string;
  runPosition?: string;
  returnedDeliveryData?: string;
  confirmedDeliveryTime?: Moment;
  confirmedReceivedBy?: string;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  invoiceLineLists?: IInvoiceLines[];
  contactPersonFullName?: string;
  contactPersonId?: number;
  salespersonPersonFullName?: string;
  salespersonPersonId?: number;
  packedByPersonFullName?: string;
  packedByPersonId?: number;
  accountsPersonFullName?: string;
  accountsPersonId?: number;
  customerId?: number;
  billToCustomerId?: number;
  deliveryMethodDeliveryMethodName?: string;
  deliveryMethodId?: number;
  orderId?: number;
}

export class Invoices implements IInvoices {
  constructor(
    public id?: number,
    public invoiceDate?: Moment,
    public customerPurchaseOrderNumber?: string,
    public isCreditNote?: boolean,
    public creditNoteReason?: string,
    public comments?: string,
    public deliveryInstructions?: string,
    public internalComments?: string,
    public totalDryItems?: number,
    public totalChillerItems?: number,
    public deliveryRun?: string,
    public runPosition?: string,
    public returnedDeliveryData?: string,
    public confirmedDeliveryTime?: Moment,
    public confirmedReceivedBy?: string,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public invoiceLineLists?: IInvoiceLines[],
    public contactPersonFullName?: string,
    public contactPersonId?: number,
    public salespersonPersonFullName?: string,
    public salespersonPersonId?: number,
    public packedByPersonFullName?: string,
    public packedByPersonId?: number,
    public accountsPersonFullName?: string,
    public accountsPersonId?: number,
    public customerId?: number,
    public billToCustomerId?: number,
    public deliveryMethodDeliveryMethodName?: string,
    public deliveryMethodId?: number,
    public orderId?: number
  ) {
    this.isCreditNote = this.isCreditNote || false;
  }
}
