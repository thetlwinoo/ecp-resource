import { Moment } from 'moment';
import { IStockItems } from 'app/shared/model/stock-items.model';

export interface IProducts {
  id?: number;
  productName?: string;
  handle?: string;
  productNumber?: string;
  searchDetails?: any;
  sellCount?: number;
  activeInd?: boolean;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  documentId?: number;
  stockItemLists?: IStockItems[];
  supplierSupplierName?: string;
  supplierId?: number;
  productCategoryName?: string;
  productCategoryId?: number;
  productBrandProductBrandName?: string;
  productBrandId?: number;
}

export class Products implements IProducts {
  constructor(
    public id?: number,
    public productName?: string,
    public handle?: string,
    public productNumber?: string,
    public searchDetails?: any,
    public sellCount?: number,
    public activeInd?: boolean,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public documentId?: number,
    public stockItemLists?: IStockItems[],
    public supplierSupplierName?: string,
    public supplierId?: number,
    public productCategoryName?: string,
    public productCategoryId?: number,
    public productBrandProductBrandName?: string,
    public productBrandId?: number
  ) {
    this.activeInd = this.activeInd || false;
  }
}
