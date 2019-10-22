import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentMethods } from 'app/shared/model/payment-methods.model';

type EntityResponseType = HttpResponse<IPaymentMethods>;
type EntityArrayResponseType = HttpResponse<IPaymentMethods[]>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodsService {
  public resourceUrl = SERVER_API_URL + 'api/payment-methods';

  constructor(protected http: HttpClient) {}

  create(paymentMethods: IPaymentMethods): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethods);
    return this.http
      .post<IPaymentMethods>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paymentMethods: IPaymentMethods): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentMethods);
    return this.http
      .put<IPaymentMethods>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaymentMethods>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentMethods[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paymentMethods: IPaymentMethods): IPaymentMethods {
    const copy: IPaymentMethods = Object.assign({}, paymentMethods, {
      validFrom:
        paymentMethods.validFrom != null && paymentMethods.validFrom.isValid() ? paymentMethods.validFrom.format(DATE_FORMAT) : null,
      validTo: paymentMethods.validTo != null && paymentMethods.validTo.isValid() ? paymentMethods.validTo.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validFrom = res.body.validFrom != null ? moment(res.body.validFrom) : null;
      res.body.validTo = res.body.validTo != null ? moment(res.body.validTo) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((paymentMethods: IPaymentMethods) => {
        paymentMethods.validFrom = paymentMethods.validFrom != null ? moment(paymentMethods.validFrom) : null;
        paymentMethods.validTo = paymentMethods.validTo != null ? moment(paymentMethods.validTo) : null;
      });
    }
    return res;
  }
}
