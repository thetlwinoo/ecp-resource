import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Wishlists } from 'app/shared/model/wishlists.model';
import { WishlistsService } from './wishlists.service';
import { WishlistsComponent } from './wishlists.component';
import { WishlistsDetailComponent } from './wishlists-detail.component';
import { WishlistsUpdateComponent } from './wishlists-update.component';
import { WishlistsDeletePopupComponent } from './wishlists-delete-dialog.component';
import { IWishlists } from 'app/shared/model/wishlists.model';

@Injectable({ providedIn: 'root' })
export class WishlistsResolve implements Resolve<IWishlists> {
  constructor(private service: WishlistsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IWishlists> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Wishlists>) => response.ok),
        map((wishlists: HttpResponse<Wishlists>) => wishlists.body)
      );
    }
    return of(new Wishlists());
  }
}

export const wishlistsRoute: Routes = [
  {
    path: '',
    component: WishlistsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.wishlists.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WishlistsDetailComponent,
    resolve: {
      wishlists: WishlistsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.wishlists.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WishlistsUpdateComponent,
    resolve: {
      wishlists: WishlistsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.wishlists.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WishlistsUpdateComponent,
    resolve: {
      wishlists: WishlistsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.wishlists.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const wishlistsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: WishlistsDeletePopupComponent,
    resolve: {
      wishlists: WishlistsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.wishlists.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
