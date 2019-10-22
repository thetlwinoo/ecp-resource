import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CompareProducts } from 'app/shared/model/compare-products.model';
import { CompareProductsService } from './compare-products.service';
import { CompareProductsComponent } from './compare-products.component';
import { CompareProductsDetailComponent } from './compare-products-detail.component';
import { CompareProductsUpdateComponent } from './compare-products-update.component';
import { CompareProductsDeletePopupComponent } from './compare-products-delete-dialog.component';
import { ICompareProducts } from 'app/shared/model/compare-products.model';

@Injectable({ providedIn: 'root' })
export class CompareProductsResolve implements Resolve<ICompareProducts> {
  constructor(private service: CompareProductsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompareProducts> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CompareProducts>) => response.ok),
        map((compareProducts: HttpResponse<CompareProducts>) => compareProducts.body)
      );
    }
    return of(new CompareProducts());
  }
}

export const compareProductsRoute: Routes = [
  {
    path: '',
    component: CompareProductsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.compareProducts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompareProductsDetailComponent,
    resolve: {
      compareProducts: CompareProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.compareProducts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompareProductsUpdateComponent,
    resolve: {
      compareProducts: CompareProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.compareProducts.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompareProductsUpdateComponent,
    resolve: {
      compareProducts: CompareProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.compareProducts.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const compareProductsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompareProductsDeletePopupComponent,
    resolve: {
      compareProducts: CompareProductsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.compareProducts.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
