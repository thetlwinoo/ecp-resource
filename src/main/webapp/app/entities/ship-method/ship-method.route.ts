import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ShipMethod } from 'app/shared/model/ship-method.model';
import { ShipMethodService } from './ship-method.service';
import { ShipMethodComponent } from './ship-method.component';
import { ShipMethodDetailComponent } from './ship-method-detail.component';
import { ShipMethodUpdateComponent } from './ship-method-update.component';
import { ShipMethodDeletePopupComponent } from './ship-method-delete-dialog.component';
import { IShipMethod } from 'app/shared/model/ship-method.model';

@Injectable({ providedIn: 'root' })
export class ShipMethodResolve implements Resolve<IShipMethod> {
  constructor(private service: ShipMethodService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IShipMethod> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ShipMethod>) => response.ok),
        map((shipMethod: HttpResponse<ShipMethod>) => shipMethod.body)
      );
    }
    return of(new ShipMethod());
  }
}

export const shipMethodRoute: Routes = [
  {
    path: '',
    component: ShipMethodComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.shipMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ShipMethodDetailComponent,
    resolve: {
      shipMethod: ShipMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.shipMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ShipMethodUpdateComponent,
    resolve: {
      shipMethod: ShipMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.shipMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ShipMethodUpdateComponent,
    resolve: {
      shipMethod: ShipMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.shipMethod.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const shipMethodPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ShipMethodDeletePopupComponent,
    resolve: {
      shipMethod: ShipMethodResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.shipMethod.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
