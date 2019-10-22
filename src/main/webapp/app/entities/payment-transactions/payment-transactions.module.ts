import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { PaymentTransactionsComponent } from './payment-transactions.component';
import { PaymentTransactionsDetailComponent } from './payment-transactions-detail.component';
import { PaymentTransactionsUpdateComponent } from './payment-transactions-update.component';
import {
  PaymentTransactionsDeletePopupComponent,
  PaymentTransactionsDeleteDialogComponent
} from './payment-transactions-delete-dialog.component';
import { paymentTransactionsRoute, paymentTransactionsPopupRoute } from './payment-transactions.route';

const ENTITY_STATES = [...paymentTransactionsRoute, ...paymentTransactionsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PaymentTransactionsComponent,
    PaymentTransactionsDetailComponent,
    PaymentTransactionsUpdateComponent,
    PaymentTransactionsDeleteDialogComponent,
    PaymentTransactionsDeletePopupComponent
  ],
  entryComponents: [PaymentTransactionsDeleteDialogComponent]
})
export class ResourcePaymentTransactionsModule {}
