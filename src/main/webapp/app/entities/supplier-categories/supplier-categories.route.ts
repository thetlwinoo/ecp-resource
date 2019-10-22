import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SupplierCategories } from 'app/shared/model/supplier-categories.model';
import { SupplierCategoriesService } from './supplier-categories.service';
import { SupplierCategoriesComponent } from './supplier-categories.component';
import { SupplierCategoriesDetailComponent } from './supplier-categories-detail.component';
import { SupplierCategoriesUpdateComponent } from './supplier-categories-update.component';
import { SupplierCategoriesDeletePopupComponent } from './supplier-categories-delete-dialog.component';
import { ISupplierCategories } from 'app/shared/model/supplier-categories.model';

@Injectable({ providedIn: 'root' })
export class SupplierCategoriesResolve implements Resolve<ISupplierCategories> {
  constructor(private service: SupplierCategoriesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISupplierCategories> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SupplierCategories>) => response.ok),
        map((supplierCategories: HttpResponse<SupplierCategories>) => supplierCategories.body)
      );
    }
    return of(new SupplierCategories());
  }
}

export const supplierCategoriesRoute: Routes = [
  {
    path: '',
    component: SupplierCategoriesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.supplierCategories.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SupplierCategoriesDetailComponent,
    resolve: {
      supplierCategories: SupplierCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.supplierCategories.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SupplierCategoriesUpdateComponent,
    resolve: {
      supplierCategories: SupplierCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.supplierCategories.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SupplierCategoriesUpdateComponent,
    resolve: {
      supplierCategories: SupplierCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.supplierCategories.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const supplierCategoriesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SupplierCategoriesDeletePopupComponent,
    resolve: {
      supplierCategories: SupplierCategoriesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.supplierCategories.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
