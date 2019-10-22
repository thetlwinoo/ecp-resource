import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { OrdersComponent } from './orders.component';
import { OrdersDetailComponent } from './orders-detail.component';
import { OrdersUpdateComponent } from './orders-update.component';
import { OrdersDeletePopupComponent, OrdersDeleteDialogComponent } from './orders-delete-dialog.component';
import { ordersRoute, ordersPopupRoute } from './orders.route';

const ENTITY_STATES = [...ordersRoute, ...ordersPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [OrdersComponent, OrdersDetailComponent, OrdersUpdateComponent, OrdersDeleteDialogComponent, OrdersDeletePopupComponent],
  entryComponents: [OrdersDeleteDialogComponent]
})
export class ResourceOrdersModule {}
