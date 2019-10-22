import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductAttributeSet } from 'app/shared/model/product-attribute-set.model';
import { ProductAttributeSetService } from './product-attribute-set.service';
import { ProductAttributeSetComponent } from './product-attribute-set.component';
import { ProductAttributeSetDetailComponent } from './product-attribute-set-detail.component';
import { ProductAttributeSetUpdateComponent } from './product-attribute-set-update.component';
import { ProductAttributeSetDeletePopupComponent } from './product-attribute-set-delete-dialog.component';
import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';

@Injectable({ providedIn: 'root' })
export class ProductAttributeSetResolve implements Resolve<IProductAttributeSet> {
  constructor(private service: ProductAttributeSetService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductAttributeSet> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductAttributeSet>) => response.ok),
        map((productAttributeSet: HttpResponse<ProductAttributeSet>) => productAttributeSet.body)
      );
    }
    return of(new ProductAttributeSet());
  }
}

export const productAttributeSetRoute: Routes = [
  {
    path: '',
    component: ProductAttributeSetComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttributeSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductAttributeSetDetailComponent,
    resolve: {
      productAttributeSet: ProductAttributeSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttributeSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductAttributeSetUpdateComponent,
    resolve: {
      productAttributeSet: ProductAttributeSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttributeSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductAttributeSetUpdateComponent,
    resolve: {
      productAttributeSet: ProductAttributeSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttributeSet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productAttributeSetPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductAttributeSetDeletePopupComponent,
    resolve: {
      productAttributeSet: ProductAttributeSetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttributeSet.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
