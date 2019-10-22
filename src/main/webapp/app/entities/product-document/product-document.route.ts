import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductDocument } from 'app/shared/model/product-document.model';
import { ProductDocumentService } from './product-document.service';
import { ProductDocumentComponent } from './product-document.component';
import { ProductDocumentDetailComponent } from './product-document-detail.component';
import { ProductDocumentUpdateComponent } from './product-document-update.component';
import { ProductDocumentDeletePopupComponent } from './product-document-delete-dialog.component';
import { IProductDocument } from 'app/shared/model/product-document.model';

@Injectable({ providedIn: 'root' })
export class ProductDocumentResolve implements Resolve<IProductDocument> {
  constructor(private service: ProductDocumentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductDocument> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductDocument>) => response.ok),
        map((productDocument: HttpResponse<ProductDocument>) => productDocument.body)
      );
    }
    return of(new ProductDocument());
  }
}

export const productDocumentRoute: Routes = [
  {
    path: '',
    component: ProductDocumentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductDocumentDetailComponent,
    resolve: {
      productDocument: ProductDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductDocumentUpdateComponent,
    resolve: {
      productDocument: ProductDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductDocumentUpdateComponent,
    resolve: {
      productDocument: ProductDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productDocumentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductDocumentDeletePopupComponent,
    resolve: {
      productDocument: ProductDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.productDocument.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
