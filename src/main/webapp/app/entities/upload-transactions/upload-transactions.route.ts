import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UploadTransactions } from 'app/shared/model/upload-transactions.model';
import { UploadTransactionsService } from './upload-transactions.service';
import { UploadTransactionsComponent } from './upload-transactions.component';
import { UploadTransactionsDetailComponent } from './upload-transactions-detail.component';
import { UploadTransactionsUpdateComponent } from './upload-transactions-update.component';
import { UploadTransactionsDeletePopupComponent } from './upload-transactions-delete-dialog.component';
import { IUploadTransactions } from 'app/shared/model/upload-transactions.model';

@Injectable({ providedIn: 'root' })
export class UploadTransactionsResolve implements Resolve<IUploadTransactions> {
  constructor(private service: UploadTransactionsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUploadTransactions> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UploadTransactions>) => response.ok),
        map((uploadTransactions: HttpResponse<UploadTransactions>) => uploadTransactions.body)
      );
    }
    return of(new UploadTransactions());
  }
}

export const uploadTransactionsRoute: Routes = [
  {
    path: '',
    component: UploadTransactionsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UploadTransactionsDetailComponent,
    resolve: {
      uploadTransactions: UploadTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UploadTransactionsUpdateComponent,
    resolve: {
      uploadTransactions: UploadTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UploadTransactionsUpdateComponent,
    resolve: {
      uploadTransactions: UploadTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadTransactions.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const uploadTransactionsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UploadTransactionsDeletePopupComponent,
    resolve: {
      uploadTransactions: UploadTransactionsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.uploadTransactions.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
