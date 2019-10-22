import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { BuyingGroupsComponent } from './buying-groups.component';
import { BuyingGroupsDetailComponent } from './buying-groups-detail.component';
import { BuyingGroupsUpdateComponent } from './buying-groups-update.component';
import { BuyingGroupsDeletePopupComponent, BuyingGroupsDeleteDialogComponent } from './buying-groups-delete-dialog.component';
import { buyingGroupsRoute, buyingGroupsPopupRoute } from './buying-groups.route';

const ENTITY_STATES = [...buyingGroupsRoute, ...buyingGroupsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BuyingGroupsComponent,
    BuyingGroupsDetailComponent,
    BuyingGroupsUpdateComponent,
    BuyingGroupsDeleteDialogComponent,
    BuyingGroupsDeletePopupComponent
  ],
  entryComponents: [BuyingGroupsDeleteDialogComponent]
})
export class ResourceBuyingGroupsModule {}
