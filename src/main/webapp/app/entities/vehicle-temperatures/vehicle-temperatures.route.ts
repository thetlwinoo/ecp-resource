import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';
import { VehicleTemperaturesService } from './vehicle-temperatures.service';
import { VehicleTemperaturesComponent } from './vehicle-temperatures.component';
import { VehicleTemperaturesDetailComponent } from './vehicle-temperatures-detail.component';
import { VehicleTemperaturesUpdateComponent } from './vehicle-temperatures-update.component';
import { VehicleTemperaturesDeletePopupComponent } from './vehicle-temperatures-delete-dialog.component';
import { IVehicleTemperatures } from 'app/shared/model/vehicle-temperatures.model';

@Injectable({ providedIn: 'root' })
export class VehicleTemperaturesResolve implements Resolve<IVehicleTemperatures> {
  constructor(private service: VehicleTemperaturesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVehicleTemperatures> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<VehicleTemperatures>) => response.ok),
        map((vehicleTemperatures: HttpResponse<VehicleTemperatures>) => vehicleTemperatures.body)
      );
    }
    return of(new VehicleTemperatures());
  }
}

export const vehicleTemperaturesRoute: Routes = [
  {
    path: '',
    component: VehicleTemperaturesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.vehicleTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VehicleTemperaturesDetailComponent,
    resolve: {
      vehicleTemperatures: VehicleTemperaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.vehicleTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VehicleTemperaturesUpdateComponent,
    resolve: {
      vehicleTemperatures: VehicleTemperaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.vehicleTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VehicleTemperaturesUpdateComponent,
    resolve: {
      vehicleTemperatures: VehicleTemperaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.vehicleTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vehicleTemperaturesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VehicleTemperaturesDeletePopupComponent,
    resolve: {
      vehicleTemperatures: VehicleTemperaturesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.vehicleTemperatures.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
