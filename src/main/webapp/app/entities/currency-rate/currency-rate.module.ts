import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { CurrencyRateComponent } from './currency-rate.component';
import { CurrencyRateDetailComponent } from './currency-rate-detail.component';
import { CurrencyRateUpdateComponent } from './currency-rate-update.component';
import { CurrencyRateDeletePopupComponent, CurrencyRateDeleteDialogComponent } from './currency-rate-delete-dialog.component';
import { currencyRateRoute, currencyRatePopupRoute } from './currency-rate.route';

const ENTITY_STATES = [...currencyRateRoute, ...currencyRatePopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CurrencyRateComponent,
    CurrencyRateDetailComponent,
    CurrencyRateUpdateComponent,
    CurrencyRateDeleteDialogComponent,
    CurrencyRateDeletePopupComponent
  ],
  entryComponents: [CurrencyRateDeleteDialogComponent]
})
export class ResourceCurrencyRateModule {}
