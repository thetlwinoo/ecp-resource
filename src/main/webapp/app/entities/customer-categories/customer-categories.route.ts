import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CustomerCategories } from 'app/shared/model/customer-categories.model';
import { CustomerCategoriesService } from './customer-categories.service';
import { CustomerCategoriesComponent } from './customer-categories.component';
import { CustomerCategoriesDetailComponent } from './customer-categories-detail.component';
import { CustomerCategoriesUpdateComponent } from './customer-categories-update.component';
import { CustomerCategoriesDeletePopupComponent } from './customer-categories-delete-dialog.component';
import { ICustomerCategories } from 'app/shared/model/customer-categories.model';

@Injectable({ providedIn: 'root' })
export class CustomerCategoriesResolve implements Resolve<ICustomerCategories> {
  constructor(private service: CustomerCategoriesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICustomerCategories> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CustomerCategories>) => response.ok),
        map((customerCategories: HttpResponse<CustomerCategories>) => customerCategories.body)
      );
    }
    return of(new CustomerCategories());
  }
}

export const customerCategoriesRoute: Routes = [
  {
    path: '',
    component: CustomerCategoriesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.customerCategories.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CustomerCategoriesDetailComponent,
    resolve: {
      customerCategories: CustomerCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.customerCategories.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CustomerCategoriesUpdateComponent,
    resolve: {
      customerCategories: CustomerCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.customerCategories.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CustomerCategoriesUpdateComponent,
    resolve: {
      customerCategories: CustomerCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.customerCategories.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerCategoriesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CustomerCategoriesDeletePopupComponent,
    resolve: {
      customerCategories: CustomerCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.customerCategories.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
