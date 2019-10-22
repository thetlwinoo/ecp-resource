import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductChoice } from 'app/shared/model/product-choice.model';
import { ProductChoiceService } from './product-choice.service';
import { ProductChoiceComponent } from './product-choice.component';
import { ProductChoiceDetailComponent } from './product-choice-detail.component';
import { ProductChoiceUpdateComponent } from './product-choice-update.component';
import { ProductChoiceDeletePopupComponent } from './product-choice-delete-dialog.component';
import { IProductChoice } from 'app/shared/model/product-choice.model';

@Injectable({ providedIn: 'root' })
export class ProductChoiceResolve implements Resolve<IProductChoice> {
  constructor(private service: ProductChoiceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductChoice> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductChoice>) => response.ok),
        map((productChoice: HttpResponse<ProductChoice>) => productChoice.body)
      );
    }
    return of(new ProductChoice());
  }
}

export const productChoiceRoute: Routes = [
  {
    path: '',
    component: ProductChoiceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productChoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductChoiceDetailComponent,
    resolve: {
      productChoice: ProductChoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productChoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductChoiceUpdateComponent,
    resolve: {
      productChoice: ProductChoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productChoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductChoiceUpdateComponent,
    resolve: {
      productChoice: ProductChoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productChoice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productChoicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductChoiceDeletePopupComponent,
    resolve: {
      productChoice: ProductChoiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productChoice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
