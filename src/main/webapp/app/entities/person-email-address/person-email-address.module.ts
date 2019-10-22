import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { PersonEmailAddressComponent } from './person-email-address.component';
import { PersonEmailAddressDetailComponent } from './person-email-address-detail.component';
import { PersonEmailAddressUpdateComponent } from './person-email-address-update.component';
import {
  PersonEmailAddressDeletePopupComponent,
  PersonEmailAddressDeleteDialogComponent
} from './person-email-address-delete-dialog.component';
import { personEmailAddressRoute, personEmailAddressPopupRoute } from './person-email-address.route';

const ENTITY_STATES = [...personEmailAddressRoute, ...personEmailAddressPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PersonEmailAddressComponent,
    PersonEmailAddressDetailComponent,
    PersonEmailAddressUpdateComponent,
    PersonEmailAddressDeleteDialogComponent,
    PersonEmailAddressDeletePopupComponent
  ],
  entryComponents: [PersonEmailAddressDeleteDialogComponent]
})
export class ResourcePersonEmailAddressModule {}
