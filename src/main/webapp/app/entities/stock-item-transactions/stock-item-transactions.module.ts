import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { StockItemTransactionsComponent } from './stock-item-transactions.component';
import { StockItemTransactionsDetailComponent } from './stock-item-transactions-detail.component';
import { StockItemTransactionsUpdateComponent } from './stock-item-transactions-update.component';
import {
  StockItemTransactionsDeletePopupComponent,
  StockItemTransactionsDeleteDialogComponent
} from './stock-item-transactions-delete-dialog.component';
import { stockItemTransactionsRoute, stockItemTransactionsPopupRoute } from './stock-item-transactions.route';

const ENTITY_STATES = [...stockItemTransactionsRoute, ...stockItemTransactionsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StockItemTransactionsComponent,
    StockItemTransactionsDetailComponent,
    StockItemTransactionsUpdateComponent,
    StockItemTransactionsDeleteDialogComponent,
    StockItemTransactionsDeletePopupComponent
  ],
  entryComponents: [StockItemTransactionsDeleteDialogComponent]
})
export class ResourceStockItemTransactionsModule {}
