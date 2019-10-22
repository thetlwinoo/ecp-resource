import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductOptionComponent } from './product-option.component';
import { ProductOptionDetailComponent } from './product-option-detail.component';
import { ProductOptionUpdateComponent } from './product-option-update.component';
import { ProductOptionDeletePopupComponent, ProductOptionDeleteDialogComponent } from './product-option-delete-dialog.component';
import { productOptionRoute, productOptionPopupRoute } from './product-option.route';

const ENTITY_STATES = [...productOptionRoute, ...productOptionPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductOptionComponent,
    ProductOptionDetailComponent,
    ProductOptionUpdateComponent,
    ProductOptionDeleteDialogComponent,
    ProductOptionDeletePopupComponent
  ],
  entryComponents: [ProductOptionDeleteDialogComponent]
})
export class ResourceProductOptionModule {}
