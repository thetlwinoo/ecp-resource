import { Moment } from 'moment';

export interface IShoppingCartItems {
  id?: number;
  quantity?: number;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  stockItemStockItemName?: string;
  stockItemId?: number;
  cartId?: number;
}

export class ShoppingCartItems implements IShoppingCartItems {
  constructor(
    public id?: number,
    public quantity?: number,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public stockItemStockItemName?: string,
    public stockItemId?: number,
    public cartId?: number
  ) {}
}
