import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UnitMeasure } from 'app/shared/model/unit-measure.model';
import { UnitMeasureService } from './unit-measure.service';
import { UnitMeasureComponent } from './unit-measure.component';
import { UnitMeasureDetailComponent } from './unit-measure-detail.component';
import { UnitMeasureUpdateComponent } from './unit-measure-update.component';
import { UnitMeasureDeletePopupComponent } from './unit-measure-delete-dialog.component';
import { IUnitMeasure } from 'app/shared/model/unit-measure.model';

@Injectable({ providedIn: 'root' })
export class UnitMeasureResolve implements Resolve<IUnitMeasure> {
  constructor(private service: UnitMeasureService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUnitMeasure> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UnitMeasure>) => response.ok),
        map((unitMeasure: HttpResponse<UnitMeasure>) => unitMeasure.body)
      );
    }
    return of(new UnitMeasure());
  }
}

export const unitMeasureRoute: Routes = [
  {
    path: '',
    component: UnitMeasureComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.unitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UnitMeasureDetailComponent,
    resolve: {
      unitMeasure: UnitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.unitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UnitMeasureUpdateComponent,
    resolve: {
      unitMeasure: UnitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.unitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UnitMeasureUpdateComponent,
    resolve: {
      unitMeasure: UnitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.unitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const unitMeasurePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UnitMeasureDeletePopupComponent,
    resolve: {
      unitMeasure: UnitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.unitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
