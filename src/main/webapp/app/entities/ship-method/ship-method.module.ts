import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ShipMethodComponent } from './ship-method.component';
import { ShipMethodDetailComponent } from './ship-method-detail.component';
import { ShipMethodUpdateComponent } from './ship-method-update.component';
import { ShipMethodDeletePopupComponent, ShipMethodDeleteDialogComponent } from './ship-method-delete-dialog.component';
import { shipMethodRoute, shipMethodPopupRoute } from './ship-method.route';

const ENTITY_STATES = [...shipMethodRoute, ...shipMethodPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ShipMethodComponent,
    ShipMethodDetailComponent,
    ShipMethodUpdateComponent,
    ShipMethodDeleteDialogComponent,
    ShipMethodDeletePopupComponent
  ],
  entryComponents: [ShipMethodDeleteDialogComponent]
})
export class ResourceShipMethodModule {}
