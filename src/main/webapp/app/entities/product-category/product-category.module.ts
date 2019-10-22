import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductCategoryComponent } from './product-category.component';
import { ProductCategoryDetailComponent } from './product-category-detail.component';
import { ProductCategoryUpdateComponent } from './product-category-update.component';
import { ProductCategoryDeletePopupComponent, ProductCategoryDeleteDialogComponent } from './product-category-delete-dialog.component';
import { productCategoryRoute, productCategoryPopupRoute } from './product-category.route';

const ENTITY_STATES = [...productCategoryRoute, ...productCategoryPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductCategoryComponent,
    ProductCategoryDetailComponent,
    ProductCategoryUpdateComponent,
    ProductCategoryDeleteDialogComponent,
    ProductCategoryDeletePopupComponent
  ],
  entryComponents: [ProductCategoryDeleteDialogComponent]
})
export class ResourceProductCategoryModule {}
