import { Moment } from 'moment';
import { IPurchaseOrderLines } from 'app/shared/model/purchase-order-lines.model';

export interface IPurchaseOrders {
  id?: number;
  orderDate?: Moment;
  expectedDeliveryDate?: Moment;
  supplierReference?: string;
  isOrderFinalized?: boolean;
  comments?: string;
  internalComments?: string;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  purchaseOrderLineLists?: IPurchaseOrderLines[];
  contactPersonFullName?: string;
  contactPersonId?: number;
  supplierSupplierName?: string;
  supplierId?: number;
  deliveryMethodDeliveryMethodName?: string;
  deliveryMethodId?: number;
}

export class PurchaseOrders implements IPurchaseOrders {
  constructor(
    public id?: number,
    public orderDate?: Moment,
    public expectedDeliveryDate?: Moment,
    public supplierReference?: string,
    public isOrderFinalized?: boolean,
    public comments?: string,
    public internalComments?: string,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public purchaseOrderLineLists?: IPurchaseOrderLines[],
    public contactPersonFullName?: string,
    public contactPersonId?: number,
    public supplierSupplierName?: string,
    public supplierId?: number,
    public deliveryMethodDeliveryMethodName?: string,
    public deliveryMethodId?: number
  ) {
    this.isOrderFinalized = this.isOrderFinalized || false;
  }
}
