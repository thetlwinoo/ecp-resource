import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PaymentMethodsService } from 'app/entities/payment-methods/payment-methods.service';
import { IPaymentMethods, PaymentMethods } from 'app/shared/model/payment-methods.model';

describe('Service Tests', () => {
  describe('PaymentMethods Service', () => {
    let injector: TestBed;
    let service: PaymentMethodsService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaymentMethods;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PaymentMethodsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PaymentMethods(0, 'AAAAAAA', false, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            validFrom: currentDate.format(DATE_FORMAT),
            validTo: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a PaymentMethods', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            validFrom: currentDate.format(DATE_FORMAT),
            validTo: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate
          },
          returnedFromService
        );
        service
          .create(new PaymentMethods(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PaymentMethods', () => {
        const returnedFromService = Object.assign(
          {
            paymentMethodName: 'BBBBBB',
            activeInd: true,
            validFrom: currentDate.format(DATE_FORMAT),
            validTo: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of PaymentMethods', () => {
        const returnedFromService = Object.assign(
          {
            paymentMethodName: 'BBBBBB',
            activeInd: true,
            validFrom: currentDate.format(DATE_FORMAT),
            validTo: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            validFrom: currentDate,
            validTo: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PaymentMethods', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
