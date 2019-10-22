import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { SpecialDealsComponent } from './special-deals.component';
import { SpecialDealsDetailComponent } from './special-deals-detail.component';
import { SpecialDealsUpdateComponent } from './special-deals-update.component';
import { SpecialDealsDeletePopupComponent, SpecialDealsDeleteDialogComponent } from './special-deals-delete-dialog.component';
import { specialDealsRoute, specialDealsPopupRoute } from './special-deals.route';

const ENTITY_STATES = [...specialDealsRoute, ...specialDealsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SpecialDealsComponent,
    SpecialDealsDetailComponent,
    SpecialDealsUpdateComponent,
    SpecialDealsDeleteDialogComponent,
    SpecialDealsDeletePopupComponent
  ],
  entryComponents: [SpecialDealsDeleteDialogComponent]
})
export class ResourceSpecialDealsModule {}
