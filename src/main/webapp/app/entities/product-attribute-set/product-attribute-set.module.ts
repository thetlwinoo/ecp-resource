import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductAttributeSetComponent } from './product-attribute-set.component';
import { ProductAttributeSetDetailComponent } from './product-attribute-set-detail.component';
import { ProductAttributeSetUpdateComponent } from './product-attribute-set-update.component';
import {
  ProductAttributeSetDeletePopupComponent,
  ProductAttributeSetDeleteDialogComponent
} from './product-attribute-set-delete-dialog.component';
import { productAttributeSetRoute, productAttributeSetPopupRoute } from './product-attribute-set.route';

const ENTITY_STATES = [...productAttributeSetRoute, ...productAttributeSetPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductAttributeSetComponent,
    ProductAttributeSetDetailComponent,
    ProductAttributeSetUpdateComponent,
    ProductAttributeSetDeleteDialogComponent,
    ProductAttributeSetDeletePopupComponent
  ],
  entryComponents: [ProductAttributeSetDeleteDialogComponent]
})
export class ResourceProductAttributeSetModule {}
