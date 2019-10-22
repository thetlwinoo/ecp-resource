export interface IWishlistProducts {
  id?: number;
  productProductName?: string;
  productId?: number;
  wishlistId?: number;
}

export class WishlistProducts implements IWishlistProducts {
  constructor(public id?: number, public productProductName?: string, public productId?: number, public wishlistId?: number) {}
}
