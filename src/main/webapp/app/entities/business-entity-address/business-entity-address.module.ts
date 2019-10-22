import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { BusinessEntityAddressComponent } from './business-entity-address.component';
import { BusinessEntityAddressDetailComponent } from './business-entity-address-detail.component';
import { BusinessEntityAddressUpdateComponent } from './business-entity-address-update.component';
import {
  BusinessEntityAddressDeletePopupComponent,
  BusinessEntityAddressDeleteDialogComponent
} from './business-entity-address-delete-dialog.component';
import { businessEntityAddressRoute, businessEntityAddressPopupRoute } from './business-entity-address.route';

const ENTITY_STATES = [...businessEntityAddressRoute, ...businessEntityAddressPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BusinessEntityAddressComponent,
    BusinessEntityAddressDetailComponent,
    BusinessEntityAddressUpdateComponent,
    BusinessEntityAddressDeleteDialogComponent,
    BusinessEntityAddressDeletePopupComponent
  ],
  entryComponents: [BusinessEntityAddressDeleteDialogComponent]
})
export class ResourceBusinessEntityAddressModule {}
