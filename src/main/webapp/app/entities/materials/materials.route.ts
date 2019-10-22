import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Materials } from 'app/shared/model/materials.model';
import { MaterialsService } from './materials.service';
import { MaterialsComponent } from './materials.component';
import { MaterialsDetailComponent } from './materials-detail.component';
import { MaterialsUpdateComponent } from './materials-update.component';
import { MaterialsDeletePopupComponent } from './materials-delete-dialog.component';
import { IMaterials } from 'app/shared/model/materials.model';

@Injectable({ providedIn: 'root' })
export class MaterialsResolve implements Resolve<IMaterials> {
  constructor(private service: MaterialsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMaterials> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Materials>) => response.ok),
        map((materials: HttpResponse<Materials>) => materials.body)
      );
    }
    return of(new Materials());
  }
}

export const materialsRoute: Routes = [
  {
    path: '',
    component: MaterialsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.materials.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MaterialsDetailComponent,
    resolve: {
      materials: MaterialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.materials.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MaterialsUpdateComponent,
    resolve: {
      materials: MaterialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.materials.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MaterialsUpdateComponent,
    resolve: {
      materials: MaterialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.materials.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const materialsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MaterialsDeletePopupComponent,
    resolve: {
      materials: MaterialsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.materials.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
