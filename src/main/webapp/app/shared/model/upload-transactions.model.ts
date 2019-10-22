import { Moment } from 'moment';
import { ISupplierImportedDocument } from 'app/shared/model/supplier-imported-document.model';
import { IStockItemTemp } from 'app/shared/model/stock-item-temp.model';

export interface IUploadTransactions {
  id?: number;
  fileName?: string;
  templateUrl?: string;
  status?: number;
  generatedCode?: string;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  importDocumentLists?: ISupplierImportedDocument[];
  stockItemTempLists?: IStockItemTemp[];
  supplierSupplierName?: string;
  supplierId?: number;
  actionTypeActionTypeName?: string;
  actionTypeId?: number;
}

export class UploadTransactions implements IUploadTransactions {
  constructor(
    public id?: number,
    public fileName?: string,
    public templateUrl?: string,
    public status?: number,
    public generatedCode?: string,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public importDocumentLists?: ISupplierImportedDocument[],
    public stockItemTempLists?: IStockItemTemp[],
    public supplierSupplierName?: string,
    public supplierId?: number,
    public actionTypeActionTypeName?: string,
    public actionTypeId?: number
  ) {}
}
