import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductOptionSet } from 'app/shared/model/product-option-set.model';
import { ProductOptionSetService } from './product-option-set.service';
import { ProductOptionSetComponent } from './product-option-set.component';
import { ProductOptionSetDetailComponent } from './product-option-set-detail.component';
import { ProductOptionSetUpdateComponent } from './product-option-set-update.component';
import { ProductOptionSetDeletePopupComponent } from './product-option-set-delete-dialog.component';
import { IProductOptionSet } from 'app/shared/model/product-option-set.model';

@Injectable({ providedIn: 'root' })
export class ProductOptionSetResolve implements Resolve<IProductOptionSet> {
  constructor(private service: ProductOptionSetService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductOptionSet> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductOptionSet>) => response.ok),
        map((productOptionSet: HttpResponse<ProductOptionSet>) => productOptionSet.body)
      );
    }
    return of(new ProductOptionSet());
  }
}

export const productOptionSetRoute: Routes = [
  {
    path: '',
    component: ProductOptionSetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductOptionSetDetailComponent,
    resolve: {
      productOptionSet: ProductOptionSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductOptionSetUpdateComponent,
    resolve: {
      productOptionSet: ProductOptionSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductOptionSetUpdateComponent,
    resolve: {
      productOptionSet: ProductOptionSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productOptionSetPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductOptionSetDeletePopupComponent,
    resolve: {
      productOptionSet: ProductOptionSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productOptionSet.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
