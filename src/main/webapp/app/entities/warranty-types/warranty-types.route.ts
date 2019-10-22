import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { WarrantyTypes } from 'app/shared/model/warranty-types.model';
import { WarrantyTypesService } from './warranty-types.service';
import { WarrantyTypesComponent } from './warranty-types.component';
import { WarrantyTypesDetailComponent } from './warranty-types-detail.component';
import { WarrantyTypesUpdateComponent } from './warranty-types-update.component';
import { WarrantyTypesDeletePopupComponent } from './warranty-types-delete-dialog.component';
import { IWarrantyTypes } from 'app/shared/model/warranty-types.model';

@Injectable({ providedIn: 'root' })
export class WarrantyTypesResolve implements Resolve<IWarrantyTypes> {
  constructor(private service: WarrantyTypesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWarrantyTypes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<WarrantyTypes>) => response.ok),
        map((warrantyTypes: HttpResponse<WarrantyTypes>) => warrantyTypes.body)
      );
    }
    return of(new WarrantyTypes());
  }
}

export const warrantyTypesRoute: Routes = [
  {
    path: '',
    component: WarrantyTypesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.warrantyTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WarrantyTypesDetailComponent,
    resolve: {
      warrantyTypes: WarrantyTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.warrantyTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WarrantyTypesUpdateComponent,
    resolve: {
      warrantyTypes: WarrantyTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.warrantyTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WarrantyTypesUpdateComponent,
    resolve: {
      warrantyTypes: WarrantyTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.warrantyTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const warrantyTypesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: WarrantyTypesDeletePopupComponent,
    resolve: {
      warrantyTypes: WarrantyTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.warrantyTypes.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
