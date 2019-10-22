import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductSetComponent } from './product-set.component';
import { ProductSetDetailComponent } from './product-set-detail.component';
import { ProductSetUpdateComponent } from './product-set-update.component';
import { ProductSetDeletePopupComponent, ProductSetDeleteDialogComponent } from './product-set-delete-dialog.component';
import { productSetRoute, productSetPopupRoute } from './product-set.route';

const ENTITY_STATES = [...productSetRoute, ...productSetPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductSetComponent,
    ProductSetDetailComponent,
    ProductSetUpdateComponent,
    ProductSetDeleteDialogComponent,
    ProductSetDeletePopupComponent
  ],
  entryComponents: [ProductSetDeleteDialogComponent]
})
export class ResourceProductSetModule {}
