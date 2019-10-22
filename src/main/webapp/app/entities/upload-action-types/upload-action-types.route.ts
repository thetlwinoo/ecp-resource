import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UploadActionTypes } from 'app/shared/model/upload-action-types.model';
import { UploadActionTypesService } from './upload-action-types.service';
import { UploadActionTypesComponent } from './upload-action-types.component';
import { UploadActionTypesDetailComponent } from './upload-action-types-detail.component';
import { UploadActionTypesUpdateComponent } from './upload-action-types-update.component';
import { UploadActionTypesDeletePopupComponent } from './upload-action-types-delete-dialog.component';
import { IUploadActionTypes } from 'app/shared/model/upload-action-types.model';

@Injectable({ providedIn: 'root' })
export class UploadActionTypesResolve implements Resolve<IUploadActionTypes> {
  constructor(private service: UploadActionTypesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUploadActionTypes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UploadActionTypes>) => response.ok),
        map((uploadActionTypes: HttpResponse<UploadActionTypes>) => uploadActionTypes.body)
      );
    }
    return of(new UploadActionTypes());
  }
}

export const uploadActionTypesRoute: Routes = [
  {
    path: '',
    component: UploadActionTypesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadActionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UploadActionTypesDetailComponent,
    resolve: {
      uploadActionTypes: UploadActionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadActionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UploadActionTypesUpdateComponent,
    resolve: {
      uploadActionTypes: UploadActionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadActionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UploadActionTypesUpdateComponent,
    resolve: {
      uploadActionTypes: UploadActionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadActionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const uploadActionTypesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UploadActionTypesDeletePopupComponent,
    resolve: {
      uploadActionTypes: UploadActionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadActionTypes.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
