import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductCatalogComponent } from './product-catalog.component';
import { ProductCatalogDetailComponent } from './product-catalog-detail.component';
import { ProductCatalogUpdateComponent } from './product-catalog-update.component';
import { ProductCatalogDeletePopupComponent, ProductCatalogDeleteDialogComponent } from './product-catalog-delete-dialog.component';
import { productCatalogRoute, productCatalogPopupRoute } from './product-catalog.route';

const ENTITY_STATES = [...productCatalogRoute, ...productCatalogPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductCatalogComponent,
    ProductCatalogDetailComponent,
    ProductCatalogUpdateComponent,
    ProductCatalogDeleteDialogComponent,
    ProductCatalogDeletePopupComponent
  ],
  entryComponents: [ProductCatalogDeleteDialogComponent]
})
export class ResourceProductCatalogModule {}
