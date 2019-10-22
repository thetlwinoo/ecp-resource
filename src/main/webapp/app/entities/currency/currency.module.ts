import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { CurrencyComponent } from './currency.component';
import { CurrencyDetailComponent } from './currency-detail.component';
import { CurrencyUpdateComponent } from './currency-update.component';
import { CurrencyDeletePopupComponent, CurrencyDeleteDialogComponent } from './currency-delete-dialog.component';
import { currencyRoute, currencyPopupRoute } from './currency.route';

const ENTITY_STATES = [...currencyRoute, ...currencyPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CurrencyComponent,
    CurrencyDetailComponent,
    CurrencyUpdateComponent,
    CurrencyDeleteDialogComponent,
    CurrencyDeletePopupComponent
  ],
  entryComponents: [CurrencyDeleteDialogComponent]
})
export class ResourceCurrencyModule {}
