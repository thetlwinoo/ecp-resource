import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';
import { ColdRoomTemperaturesService } from './cold-room-temperatures.service';
import { ColdRoomTemperaturesComponent } from './cold-room-temperatures.component';
import { ColdRoomTemperaturesDetailComponent } from './cold-room-temperatures-detail.component';
import { ColdRoomTemperaturesUpdateComponent } from './cold-room-temperatures-update.component';
import { ColdRoomTemperaturesDeletePopupComponent } from './cold-room-temperatures-delete-dialog.component';
import { IColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';

@Injectable({ providedIn: 'root' })
export class ColdRoomTemperaturesResolve implements Resolve<IColdRoomTemperatures> {
  constructor(private service: ColdRoomTemperaturesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IColdRoomTemperatures> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ColdRoomTemperatures>) => response.ok),
        map((coldRoomTemperatures: HttpResponse<ColdRoomTemperatures>) => coldRoomTemperatures.body)
      );
    }
    return of(new ColdRoomTemperatures());
  }
}

export const coldRoomTemperaturesRoute: Routes = [
  {
    path: '',
    component: ColdRoomTemperaturesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.coldRoomTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ColdRoomTemperaturesDetailComponent,
    resolve: {
      coldRoomTemperatures: ColdRoomTemperaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.coldRoomTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ColdRoomTemperaturesUpdateComponent,
    resolve: {
      coldRoomTemperatures: ColdRoomTemperaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.coldRoomTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ColdRoomTemperaturesUpdateComponent,
    resolve: {
      coldRoomTemperatures: ColdRoomTemperaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.coldRoomTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const coldRoomTemperaturesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ColdRoomTemperaturesDeletePopupComponent,
    resolve: {
      coldRoomTemperatures: ColdRoomTemperaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.coldRoomTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
