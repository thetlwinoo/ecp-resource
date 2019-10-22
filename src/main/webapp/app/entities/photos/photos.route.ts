import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Photos } from 'app/shared/model/photos.model';
import { PhotosService } from './photos.service';
import { PhotosComponent } from './photos.component';
import { PhotosDetailComponent } from './photos-detail.component';
import { PhotosUpdateComponent } from './photos-update.component';
import { PhotosDeletePopupComponent } from './photos-delete-dialog.component';
import { IPhotos } from 'app/shared/model/photos.model';

@Injectable({ providedIn: 'root' })
export class PhotosResolve implements Resolve<IPhotos> {
  constructor(private service: PhotosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPhotos> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Photos>) => response.ok),
        map((photos: HttpResponse<Photos>) => photos.body)
      );
    }
    return of(new Photos());
  }
}

export const photosRoute: Routes = [
  {
    path: '',
    component: PhotosComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.photos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PhotosDetailComponent,
    resolve: {
      photos: PhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.photos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PhotosUpdateComponent,
    resolve: {
      photos: PhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.photos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PhotosUpdateComponent,
    resolve: {
      photos: PhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.photos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const photosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PhotosDeletePopupComponent,
    resolve: {
      photos: PhotosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.photos.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
