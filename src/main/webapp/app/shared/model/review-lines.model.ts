import { Moment } from 'moment';

export interface IReviewLines {
  id?: number;
  productRating?: number;
  productReview?: any;
  sellerRating?: number;
  sellerReview?: any;
  deliveryRating?: number;
  deliveryReview?: any;
  photoContentType?: string;
  photo?: any;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  stockItemId?: number;
  reviewId?: number;
}

export class ReviewLines implements IReviewLines {
  constructor(
    public id?: number,
    public productRating?: number,
    public productReview?: any,
    public sellerRating?: number,
    public sellerReview?: any,
    public deliveryRating?: number,
    public deliveryReview?: any,
    public photoContentType?: string,
    public photo?: any,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public stockItemId?: number,
    public reviewId?: number
  ) {}
}
