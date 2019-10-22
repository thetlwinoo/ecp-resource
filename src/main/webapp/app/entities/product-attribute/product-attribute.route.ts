import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductAttribute } from 'app/shared/model/product-attribute.model';
import { ProductAttributeService } from './product-attribute.service';
import { ProductAttributeComponent } from './product-attribute.component';
import { ProductAttributeDetailComponent } from './product-attribute-detail.component';
import { ProductAttributeUpdateComponent } from './product-attribute-update.component';
import { ProductAttributeDeletePopupComponent } from './product-attribute-delete-dialog.component';
import { IProductAttribute } from 'app/shared/model/product-attribute.model';

@Injectable({ providedIn: 'root' })
export class ProductAttributeResolve implements Resolve<IProductAttribute> {
  constructor(private service: ProductAttributeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductAttribute> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductAttribute>) => response.ok),
        map((productAttribute: HttpResponse<ProductAttribute>) => productAttribute.body)
      );
    }
    return of(new ProductAttribute());
  }
}

export const productAttributeRoute: Routes = [
  {
    path: '',
    component: ProductAttributeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductAttributeDetailComponent,
    resolve: {
      productAttribute: ProductAttributeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductAttributeUpdateComponent,
    resolve: {
      productAttribute: ProductAttributeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductAttributeUpdateComponent,
    resolve: {
      productAttribute: ProductAttributeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productAttributePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductAttributeDeletePopupComponent,
    resolve: {
      productAttribute: ProductAttributeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
