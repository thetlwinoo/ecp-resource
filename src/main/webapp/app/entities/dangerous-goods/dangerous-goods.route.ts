import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DangerousGoods } from 'app/shared/model/dangerous-goods.model';
import { DangerousGoodsService } from './dangerous-goods.service';
import { DangerousGoodsComponent } from './dangerous-goods.component';
import { DangerousGoodsDetailComponent } from './dangerous-goods-detail.component';
import { DangerousGoodsUpdateComponent } from './dangerous-goods-update.component';
import { DangerousGoodsDeletePopupComponent } from './dangerous-goods-delete-dialog.component';
import { IDangerousGoods } from 'app/shared/model/dangerous-goods.model';

@Injectable({ providedIn: 'root' })
export class DangerousGoodsResolve implements Resolve<IDangerousGoods> {
  constructor(private service: DangerousGoodsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDangerousGoods> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DangerousGoods>) => response.ok),
        map((dangerousGoods: HttpResponse<DangerousGoods>) => dangerousGoods.body)
      );
    }
    return of(new DangerousGoods());
  }
}

export const dangerousGoodsRoute: Routes = [
  {
    path: '',
    component: DangerousGoodsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.dangerousGoods.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DangerousGoodsDetailComponent,
    resolve: {
      dangerousGoods: DangerousGoodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.dangerousGoods.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DangerousGoodsUpdateComponent,
    resolve: {
      dangerousGoods: DangerousGoodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.dangerousGoods.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DangerousGoodsUpdateComponent,
    resolve: {
      dangerousGoods: DangerousGoodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.dangerousGoods.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dangerousGoodsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DangerousGoodsDeletePopupComponent,
    resolve: {
      dangerousGoods: DangerousGoodsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.dangerousGoods.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
