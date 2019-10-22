import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { PaymentMethodsComponent } from './payment-methods.component';
import { PaymentMethodsDetailComponent } from './payment-methods-detail.component';
import { PaymentMethodsUpdateComponent } from './payment-methods-update.component';
import { PaymentMethodsDeletePopupComponent, PaymentMethodsDeleteDialogComponent } from './payment-methods-delete-dialog.component';
import { paymentMethodsRoute, paymentMethodsPopupRoute } from './payment-methods.route';

const ENTITY_STATES = [...paymentMethodsRoute, ...paymentMethodsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PaymentMethodsComponent,
    PaymentMethodsDetailComponent,
    PaymentMethodsUpdateComponent,
    PaymentMethodsDeleteDialogComponent,
    PaymentMethodsDeletePopupComponent
  ],
  entryComponents: [PaymentMethodsDeleteDialogComponent]
})
export class ResourcePaymentMethodsModule {}
