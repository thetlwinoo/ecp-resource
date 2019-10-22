import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { StockItemTempComponent } from './stock-item-temp.component';
import { StockItemTempDetailComponent } from './stock-item-temp-detail.component';
import { StockItemTempUpdateComponent } from './stock-item-temp-update.component';
import { StockItemTempDeletePopupComponent, StockItemTempDeleteDialogComponent } from './stock-item-temp-delete-dialog.component';
import { stockItemTempRoute, stockItemTempPopupRoute } from './stock-item-temp.route';

const ENTITY_STATES = [...stockItemTempRoute, ...stockItemTempPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StockItemTempComponent,
    StockItemTempDetailComponent,
    StockItemTempUpdateComponent,
    StockItemTempDeleteDialogComponent,
    StockItemTempDeletePopupComponent
  ],
  entryComponents: [StockItemTempDeleteDialogComponent]
})
export class ResourceStockItemTempModule {}
