import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { TransactionTypesComponent } from './transaction-types.component';
import { TransactionTypesDetailComponent } from './transaction-types-detail.component';
import { TransactionTypesUpdateComponent } from './transaction-types-update.component';
import { TransactionTypesDeletePopupComponent, TransactionTypesDeleteDialogComponent } from './transaction-types-delete-dialog.component';
import { transactionTypesRoute, transactionTypesPopupRoute } from './transaction-types.route';

const ENTITY_STATES = [...transactionTypesRoute, ...transactionTypesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionTypesComponent,
    TransactionTypesDetailComponent,
    TransactionTypesUpdateComponent,
    TransactionTypesDeleteDialogComponent,
    TransactionTypesDeletePopupComponent
  ],
  entryComponents: [TransactionTypesDeleteDialogComponent]
})
export class ResourceTransactionTypesModule {}
