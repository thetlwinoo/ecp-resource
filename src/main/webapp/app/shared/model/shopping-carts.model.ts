import { Moment } from 'moment';
import { IShoppingCartItems } from 'app/shared/model/shopping-cart-items.model';

export interface IShoppingCarts {
  id?: number;
  totalPrice?: number;
  totalCargoPrice?: number;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  specialDealsId?: number;
  cartUserId?: number;
  cartItemLists?: IShoppingCartItems[];
  customerId?: number;
}

export class ShoppingCarts implements IShoppingCarts {
  constructor(
    public id?: number,
    public totalPrice?: number,
    public totalCargoPrice?: number,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public specialDealsId?: number,
    public cartUserId?: number,
    public cartItemLists?: IShoppingCartItems[],
    public customerId?: number
  ) {}
}
