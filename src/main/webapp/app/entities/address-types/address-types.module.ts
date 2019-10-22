import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { AddressTypesComponent } from './address-types.component';
import { AddressTypesDetailComponent } from './address-types-detail.component';
import { AddressTypesUpdateComponent } from './address-types-update.component';
import { AddressTypesDeletePopupComponent, AddressTypesDeleteDialogComponent } from './address-types-delete-dialog.component';
import { addressTypesRoute, addressTypesPopupRoute } from './address-types.route';

const ENTITY_STATES = [...addressTypesRoute, ...addressTypesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AddressTypesComponent,
    AddressTypesDetailComponent,
    AddressTypesUpdateComponent,
    AddressTypesDeleteDialogComponent,
    AddressTypesDeletePopupComponent
  ],
  entryComponents: [AddressTypesDeleteDialogComponent]
})
export class ResourceAddressTypesModule {}
