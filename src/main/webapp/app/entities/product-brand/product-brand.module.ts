import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductBrandComponent } from './product-brand.component';
import { ProductBrandDetailComponent } from './product-brand-detail.component';
import { ProductBrandUpdateComponent } from './product-brand-update.component';
import { ProductBrandDeletePopupComponent, ProductBrandDeleteDialogComponent } from './product-brand-delete-dialog.component';
import { productBrandRoute, productBrandPopupRoute } from './product-brand.route';

const ENTITY_STATES = [...productBrandRoute, ...productBrandPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductBrandComponent,
    ProductBrandDetailComponent,
    ProductBrandUpdateComponent,
    ProductBrandDeleteDialogComponent,
    ProductBrandDeletePopupComponent
  ],
  entryComponents: [ProductBrandDeleteDialogComponent]
})
export class ResourceProductBrandModule {}
