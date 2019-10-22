import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductSet } from 'app/shared/model/product-set.model';
import { ProductSetService } from './product-set.service';
import { ProductSetComponent } from './product-set.component';
import { ProductSetDetailComponent } from './product-set-detail.component';
import { ProductSetUpdateComponent } from './product-set-update.component';
import { ProductSetDeletePopupComponent } from './product-set-delete-dialog.component';
import { IProductSet } from 'app/shared/model/product-set.model';

@Injectable({ providedIn: 'root' })
export class ProductSetResolve implements Resolve<IProductSet> {
  constructor(private service: ProductSetService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductSet> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductSet>) => response.ok),
        map((productSet: HttpResponse<ProductSet>) => productSet.body)
      );
    }
    return of(new ProductSet());
  }
}

export const productSetRoute: Routes = [
  {
    path: '',
    component: ProductSetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductSetDetailComponent,
    resolve: {
      productSet: ProductSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductSetUpdateComponent,
    resolve: {
      productSet: ProductSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductSetUpdateComponent,
    resolve: {
      productSet: ProductSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productSetPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductSetDeletePopupComponent,
    resolve: {
      productSet: ProductSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productSet.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
