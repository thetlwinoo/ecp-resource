import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductTags } from 'app/shared/model/product-tags.model';
import { ProductTagsService } from './product-tags.service';
import { ProductTagsComponent } from './product-tags.component';
import { ProductTagsDetailComponent } from './product-tags-detail.component';
import { ProductTagsUpdateComponent } from './product-tags-update.component';
import { ProductTagsDeletePopupComponent } from './product-tags-delete-dialog.component';
import { IProductTags } from 'app/shared/model/product-tags.model';

@Injectable({ providedIn: 'root' })
export class ProductTagsResolve implements Resolve<IProductTags> {
  constructor(private service: ProductTagsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductTags> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductTags>) => response.ok),
        map((productTags: HttpResponse<ProductTags>) => productTags.body)
      );
    }
    return of(new ProductTags());
  }
}

export const productTagsRoute: Routes = [
  {
    path: '',
    component: ProductTagsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productTags.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductTagsDetailComponent,
    resolve: {
      productTags: ProductTagsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productTags.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductTagsUpdateComponent,
    resolve: {
      productTags: ProductTagsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productTags.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductTagsUpdateComponent,
    resolve: {
      productTags: ProductTagsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productTags.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productTagsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductTagsDeletePopupComponent,
    resolve: {
      productTags: ProductTagsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productTags.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
