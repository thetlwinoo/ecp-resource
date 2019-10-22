import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PaymentMethods } from 'app/shared/model/payment-methods.model';
import { PaymentMethodsService } from './payment-methods.service';
import { PaymentMethodsComponent } from './payment-methods.component';
import { PaymentMethodsDetailComponent } from './payment-methods-detail.component';
import { PaymentMethodsUpdateComponent } from './payment-methods-update.component';
import { PaymentMethodsDeletePopupComponent } from './payment-methods-delete-dialog.component';
import { IPaymentMethods } from 'app/shared/model/payment-methods.model';

@Injectable({ providedIn: 'root' })
export class PaymentMethodsResolve implements Resolve<IPaymentMethods> {
  constructor(private service: PaymentMethodsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPaymentMethods> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PaymentMethods>) => response.ok),
        map((paymentMethods: HttpResponse<PaymentMethods>) => paymentMethods.body)
      );
    }
    return of(new PaymentMethods());
  }
}

export const paymentMethodsRoute: Routes = [
  {
    path: '',
    component: PaymentMethodsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.paymentMethods.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaymentMethodsDetailComponent,
    resolve: {
      paymentMethods: PaymentMethodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.paymentMethods.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaymentMethodsUpdateComponent,
    resolve: {
      paymentMethods: PaymentMethodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.paymentMethods.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaymentMethodsUpdateComponent,
    resolve: {
      paymentMethods: PaymentMethodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.paymentMethods.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const paymentMethodsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PaymentMethodsDeletePopupComponent,
    resolve: {
      paymentMethods: PaymentMethodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.paymentMethods.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
