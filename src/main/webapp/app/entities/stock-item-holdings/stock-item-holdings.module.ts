import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { StockItemHoldingsComponent } from './stock-item-holdings.component';
import { StockItemHoldingsDetailComponent } from './stock-item-holdings-detail.component';
import { StockItemHoldingsUpdateComponent } from './stock-item-holdings-update.component';
import {
  StockItemHoldingsDeletePopupComponent,
  StockItemHoldingsDeleteDialogComponent
} from './stock-item-holdings-delete-dialog.component';
import { stockItemHoldingsRoute, stockItemHoldingsPopupRoute } from './stock-item-holdings.route';

const ENTITY_STATES = [...stockItemHoldingsRoute, ...stockItemHoldingsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StockItemHoldingsComponent,
    StockItemHoldingsDetailComponent,
    StockItemHoldingsUpdateComponent,
    StockItemHoldingsDeleteDialogComponent,
    StockItemHoldingsDeletePopupComponent
  ],
  entryComponents: [StockItemHoldingsDeleteDialogComponent]
})
export class ResourceStockItemHoldingsModule {}
