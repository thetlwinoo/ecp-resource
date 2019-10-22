import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { PhoneNumberTypeComponent } from './phone-number-type.component';
import { PhoneNumberTypeDetailComponent } from './phone-number-type-detail.component';
import { PhoneNumberTypeUpdateComponent } from './phone-number-type-update.component';
import { PhoneNumberTypeDeletePopupComponent, PhoneNumberTypeDeleteDialogComponent } from './phone-number-type-delete-dialog.component';
import { phoneNumberTypeRoute, phoneNumberTypePopupRoute } from './phone-number-type.route';

const ENTITY_STATES = [...phoneNumberTypeRoute, ...phoneNumberTypePopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PhoneNumberTypeComponent,
    PhoneNumberTypeDetailComponent,
    PhoneNumberTypeUpdateComponent,
    PhoneNumberTypeDeleteDialogComponent,
    PhoneNumberTypeDeletePopupComponent
  ],
  entryComponents: [PhoneNumberTypeDeleteDialogComponent]
})
export class ResourcePhoneNumberTypeModule {}
