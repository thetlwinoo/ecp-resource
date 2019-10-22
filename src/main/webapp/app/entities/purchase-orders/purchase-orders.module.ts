import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { PurchaseOrdersComponent } from './purchase-orders.component';
import { PurchaseOrdersDetailComponent } from './purchase-orders-detail.component';
import { PurchaseOrdersUpdateComponent } from './purchase-orders-update.component';
import { PurchaseOrdersDeletePopupComponent, PurchaseOrdersDeleteDialogComponent } from './purchase-orders-delete-dialog.component';
import { purchaseOrdersRoute, purchaseOrdersPopupRoute } from './purchase-orders.route';

const ENTITY_STATES = [...purchaseOrdersRoute, ...purchaseOrdersPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PurchaseOrdersComponent,
    PurchaseOrdersDetailComponent,
    PurchaseOrdersUpdateComponent,
    PurchaseOrdersDeleteDialogComponent,
    PurchaseOrdersDeletePopupComponent
  ],
  entryComponents: [PurchaseOrdersDeleteDialogComponent]
})
export class ResourcePurchaseOrdersModule {}
