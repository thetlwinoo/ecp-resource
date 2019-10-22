import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cities } from 'app/shared/model/cities.model';
import { CitiesService } from './cities.service';
import { CitiesComponent } from './cities.component';
import { CitiesDetailComponent } from './cities-detail.component';
import { CitiesUpdateComponent } from './cities-update.component';
import { CitiesDeletePopupComponent } from './cities-delete-dialog.component';
import { ICities } from 'app/shared/model/cities.model';

@Injectable({ providedIn: 'root' })
export class CitiesResolve implements Resolve<ICities> {
  constructor(private service: CitiesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICities> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Cities>) => response.ok),
        map((cities: HttpResponse<Cities>) => cities.body)
      );
    }
    return of(new Cities());
  }
}

export const citiesRoute: Routes = [
  {
    path: '',
    component: CitiesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.cities.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CitiesDetailComponent,
    resolve: {
      cities: CitiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.cities.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CitiesUpdateComponent,
    resolve: {
      cities: CitiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.cities.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CitiesUpdateComponent,
    resolve: {
      cities: CitiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.cities.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const citiesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CitiesDeletePopupComponent,
    resolve: {
      cities: CitiesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.cities.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
