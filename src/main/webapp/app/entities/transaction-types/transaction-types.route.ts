import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionTypes } from 'app/shared/model/transaction-types.model';
import { TransactionTypesService } from './transaction-types.service';
import { TransactionTypesComponent } from './transaction-types.component';
import { TransactionTypesDetailComponent } from './transaction-types-detail.component';
import { TransactionTypesUpdateComponent } from './transaction-types-update.component';
import { TransactionTypesDeletePopupComponent } from './transaction-types-delete-dialog.component';
import { ITransactionTypes } from 'app/shared/model/transaction-types.model';

@Injectable({ providedIn: 'root' })
export class TransactionTypesResolve implements Resolve<ITransactionTypes> {
  constructor(private service: TransactionTypesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionTypes> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionTypes>) => response.ok),
        map((transactionTypes: HttpResponse<TransactionTypes>) => transactionTypes.body)
      );
    }
    return of(new TransactionTypes());
  }
}

export const transactionTypesRoute: Routes = [
  {
    path: '',
    component: TransactionTypesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.transactionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionTypesDetailComponent,
    resolve: {
      transactionTypes: TransactionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.transactionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionTypesUpdateComponent,
    resolve: {
      transactionTypes: TransactionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.transactionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionTypesUpdateComponent,
    resolve: {
      transactionTypes: TransactionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.transactionTypes.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionTypesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionTypesDeletePopupComponent,
    resolve: {
      transactionTypes: TransactionTypesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.transactionTypes.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
