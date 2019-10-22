import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InvoiceLines } from 'app/shared/model/invoice-lines.model';
import { InvoiceLinesService } from './invoice-lines.service';
import { InvoiceLinesComponent } from './invoice-lines.component';
import { InvoiceLinesDetailComponent } from './invoice-lines-detail.component';
import { InvoiceLinesUpdateComponent } from './invoice-lines-update.component';
import { InvoiceLinesDeletePopupComponent } from './invoice-lines-delete-dialog.component';
import { IInvoiceLines } from 'app/shared/model/invoice-lines.model';

@Injectable({ providedIn: 'root' })
export class InvoiceLinesResolve implements Resolve<IInvoiceLines> {
  constructor(private service: InvoiceLinesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInvoiceLines> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InvoiceLines>) => response.ok),
        map((invoiceLines: HttpResponse<InvoiceLines>) => invoiceLines.body)
      );
    }
    return of(new InvoiceLines());
  }
}

export const invoiceLinesRoute: Routes = [
  {
    path: '',
    component: InvoiceLinesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.invoiceLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvoiceLinesDetailComponent,
    resolve: {
      invoiceLines: InvoiceLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.invoiceLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvoiceLinesUpdateComponent,
    resolve: {
      invoiceLines: InvoiceLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.invoiceLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvoiceLinesUpdateComponent,
    resolve: {
      invoiceLines: InvoiceLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.invoiceLines.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const invoiceLinesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InvoiceLinesDeletePopupComponent,
    resolve: {
      invoiceLines: InvoiceLinesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.invoiceLines.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
