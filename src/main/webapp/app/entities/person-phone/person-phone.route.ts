import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonPhone } from 'app/shared/model/person-phone.model';
import { PersonPhoneService } from './person-phone.service';
import { PersonPhoneComponent } from './person-phone.component';
import { PersonPhoneDetailComponent } from './person-phone-detail.component';
import { PersonPhoneUpdateComponent } from './person-phone-update.component';
import { PersonPhoneDeletePopupComponent } from './person-phone-delete-dialog.component';
import { IPersonPhone } from 'app/shared/model/person-phone.model';

@Injectable({ providedIn: 'root' })
export class PersonPhoneResolve implements Resolve<IPersonPhone> {
  constructor(private service: PersonPhoneService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonPhone> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PersonPhone>) => response.ok),
        map((personPhone: HttpResponse<PersonPhone>) => personPhone.body)
      );
    }
    return of(new PersonPhone());
  }
}

export const personPhoneRoute: Routes = [
  {
    path: '',
    component: PersonPhoneComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.personPhone.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonPhoneDetailComponent,
    resolve: {
      personPhone: PersonPhoneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.personPhone.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonPhoneUpdateComponent,
    resolve: {
      personPhone: PersonPhoneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.personPhone.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonPhoneUpdateComponent,
    resolve: {
      personPhone: PersonPhoneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.personPhone.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const personPhonePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PersonPhoneDeletePopupComponent,
    resolve: {
      personPhone: PersonPhoneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'resourceApp.personPhone.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
