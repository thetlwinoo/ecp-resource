import { Moment } from 'moment';

export interface IPurchaseOrderLines {
  id?: number;
  ordersOuters?: number;
  description?: string;
  receivedOuters?: number;
  expectedUnitPricePerOuter?: number;
  lastReceiptDate?: Moment;
  isOrderLineFinalized?: boolean;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  packageTypePackageTypeName?: string;
  packageTypeId?: number;
  stockItemStockItemName?: string;
  stockItemId?: number;
  purchaseOrderId?: number;
}

export class PurchaseOrderLines implements IPurchaseOrderLines {
  constructor(
    public id?: number,
    public ordersOuters?: number,
    public description?: string,
    public receivedOuters?: number,
    public expectedUnitPricePerOuter?: number,
    public lastReceiptDate?: Moment,
    public isOrderLineFinalized?: boolean,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public packageTypePackageTypeName?: string,
    public packageTypeId?: number,
    public stockItemStockItemName?: string,
    public stockItemId?: number,
    public purchaseOrderId?: number
  ) {
    this.isOrderLineFinalized = this.isOrderLineFinalized || false;
  }
}
