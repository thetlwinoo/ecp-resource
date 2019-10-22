import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OrderLines } from 'app/shared/model/order-lines.model';
import { OrderLinesService } from './order-lines.service';
import { OrderLinesComponent } from './order-lines.component';
import { OrderLinesDetailComponent } from './order-lines-detail.component';
import { OrderLinesUpdateComponent } from './order-lines-update.component';
import { OrderLinesDeletePopupComponent } from './order-lines-delete-dialog.component';
import { IOrderLines } from 'app/shared/model/order-lines.model';

@Injectable({ providedIn: 'root' })
export class OrderLinesResolve implements Resolve<IOrderLines> {
  constructor(private service: OrderLinesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOrderLines> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OrderLines>) => response.ok),
        map((orderLines: HttpResponse<OrderLines>) => orderLines.body)
      );
    }
    return of(new OrderLines());
  }
}

export const orderLinesRoute: Routes = [
  {
    path: '',
    component: OrderLinesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.orderLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OrderLinesDetailComponent,
    resolve: {
      orderLines: OrderLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.orderLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OrderLinesUpdateComponent,
    resolve: {
      orderLines: OrderLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.orderLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OrderLinesUpdateComponent,
    resolve: {
      orderLines: OrderLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.orderLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const orderLinesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OrderLinesDeletePopupComponent,
    resolve: {
      orderLines: OrderLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.orderLines.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
