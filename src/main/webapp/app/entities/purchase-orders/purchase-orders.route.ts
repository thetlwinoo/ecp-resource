import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PurchaseOrders } from 'app/shared/model/purchase-orders.model';
import { PurchaseOrdersService } from './purchase-orders.service';
import { PurchaseOrdersComponent } from './purchase-orders.component';
import { PurchaseOrdersDetailComponent } from './purchase-orders-detail.component';
import { PurchaseOrdersUpdateComponent } from './purchase-orders-update.component';
import { PurchaseOrdersDeletePopupComponent } from './purchase-orders-delete-dialog.component';
import { IPurchaseOrders } from 'app/shared/model/purchase-orders.model';

@Injectable({ providedIn: 'root' })
export class PurchaseOrdersResolve implements Resolve<IPurchaseOrders> {
  constructor(private service: PurchaseOrdersService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPurchaseOrders> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PurchaseOrders>) => response.ok),
        map((purchaseOrders: HttpResponse<PurchaseOrders>) => purchaseOrders.body)
      );
    }
    return of(new PurchaseOrders());
  }
}

export const purchaseOrdersRoute: Routes = [
  {
    path: '',
    component: PurchaseOrdersComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.purchaseOrders.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PurchaseOrdersDetailComponent,
    resolve: {
      purchaseOrders: PurchaseOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.purchaseOrders.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PurchaseOrdersUpdateComponent,
    resolve: {
      purchaseOrders: PurchaseOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.purchaseOrders.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PurchaseOrdersUpdateComponent,
    resolve: {
      purchaseOrders: PurchaseOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.purchaseOrders.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const purchaseOrdersPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PurchaseOrdersDeletePopupComponent,
    resolve: {
      purchaseOrders: PurchaseOrdersResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.purchaseOrders.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
