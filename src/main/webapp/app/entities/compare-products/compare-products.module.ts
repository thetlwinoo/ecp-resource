import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { CompareProductsComponent } from './compare-products.component';
import { CompareProductsDetailComponent } from './compare-products-detail.component';
import { CompareProductsUpdateComponent } from './compare-products-update.component';
import { CompareProductsDeletePopupComponent, CompareProductsDeleteDialogComponent } from './compare-products-delete-dialog.component';
import { compareProductsRoute, compareProductsPopupRoute } from './compare-products.route';

const ENTITY_STATES = [...compareProductsRoute, ...compareProductsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompareProductsComponent,
    CompareProductsDetailComponent,
    CompareProductsUpdateComponent,
    CompareProductsDeleteDialogComponent,
    CompareProductsDeletePopupComponent
  ],
  entryComponents: [CompareProductsDeleteDialogComponent]
})
export class ResourceCompareProductsModule {}
