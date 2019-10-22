import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Reviews } from 'app/shared/model/reviews.model';
import { ReviewsService } from './reviews.service';
import { ReviewsComponent } from './reviews.component';
import { ReviewsDetailComponent } from './reviews-detail.component';
import { ReviewsUpdateComponent } from './reviews-update.component';
import { ReviewsDeletePopupComponent } from './reviews-delete-dialog.component';
import { IReviews } from 'app/shared/model/reviews.model';

@Injectable({ providedIn: 'root' })
export class ReviewsResolve implements Resolve<IReviews> {
  constructor(private service: ReviewsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReviews> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Reviews>) => response.ok),
        map((reviews: HttpResponse<Reviews>) => reviews.body)
      );
    }
    return of(new Reviews());
  }
}

export const reviewsRoute: Routes = [
  {
    path: '',
    component: ReviewsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.reviews.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReviewsDetailComponent,
    resolve: {
      reviews: ReviewsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.reviews.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReviewsUpdateComponent,
    resolve: {
      reviews: ReviewsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.reviews.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReviewsUpdateComponent,
    resolve: {
      reviews: ReviewsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.reviews.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const reviewsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReviewsDeletePopupComponent,
    resolve: {
      reviews: ReviewsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.reviews.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
