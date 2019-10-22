import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductTagsComponent } from './product-tags.component';
import { ProductTagsDetailComponent } from './product-tags-detail.component';
import { ProductTagsUpdateComponent } from './product-tags-update.component';
import { ProductTagsDeletePopupComponent, ProductTagsDeleteDialogComponent } from './product-tags-delete-dialog.component';
import { productTagsRoute, productTagsPopupRoute } from './product-tags.route';

const ENTITY_STATES = [...productTagsRoute, ...productTagsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductTagsComponent,
    ProductTagsDetailComponent,
    ProductTagsUpdateComponent,
    ProductTagsDeleteDialogComponent,
    ProductTagsDeletePopupComponent
  ],
  entryComponents: [ProductTagsDeleteDialogComponent]
})
export class ResourceProductTagsModule {}
