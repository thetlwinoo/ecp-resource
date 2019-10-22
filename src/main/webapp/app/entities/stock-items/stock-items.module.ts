import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { StockItemsComponent } from './stock-items.component';
import { StockItemsDetailComponent } from './stock-items-detail.component';
import { StockItemsUpdateComponent } from './stock-items-update.component';
import { StockItemsDeletePopupComponent, StockItemsDeleteDialogComponent } from './stock-items-delete-dialog.component';
import { stockItemsRoute, stockItemsPopupRoute } from './stock-items.route';

const ENTITY_STATES = [...stockItemsRoute, ...stockItemsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StockItemsComponent,
    StockItemsDetailComponent,
    StockItemsUpdateComponent,
    StockItemsDeleteDialogComponent,
    StockItemsDeletePopupComponent
  ],
  entryComponents: [StockItemsDeleteDialogComponent]
})
export class ResourceStockItemsModule {}
