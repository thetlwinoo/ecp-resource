import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StateProvinces } from 'app/shared/model/state-provinces.model';
import { StateProvincesService } from './state-provinces.service';
import { StateProvincesComponent } from './state-provinces.component';
import { StateProvincesDetailComponent } from './state-provinces-detail.component';
import { StateProvincesUpdateComponent } from './state-provinces-update.component';
import { StateProvincesDeletePopupComponent } from './state-provinces-delete-dialog.component';
import { IStateProvinces } from 'app/shared/model/state-provinces.model';

@Injectable({ providedIn: 'root' })
export class StateProvincesResolve implements Resolve<IStateProvinces> {
  constructor(private service: StateProvincesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStateProvinces> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<StateProvinces>) => response.ok),
        map((stateProvinces: HttpResponse<StateProvinces>) => stateProvinces.body)
      );
    }
    return of(new StateProvinces());
  }
}

export const stateProvincesRoute: Routes = [
  {
    path: '',
    component: StateProvincesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.stateProvinces.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StateProvincesDetailComponent,
    resolve: {
      stateProvinces: StateProvincesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.stateProvinces.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StateProvincesUpdateComponent,
    resolve: {
      stateProvinces: StateProvincesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.stateProvinces.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StateProvincesUpdateComponent,
    resolve: {
      stateProvinces: StateProvincesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.stateProvinces.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const stateProvincesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: StateProvincesDeletePopupComponent,
    resolve: {
      stateProvinces: StateProvincesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.stateProvinces.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
