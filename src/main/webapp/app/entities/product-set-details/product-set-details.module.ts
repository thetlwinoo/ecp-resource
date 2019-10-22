import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductSetDetailsComponent } from './product-set-details.component';
import { ProductSetDetailsDetailComponent } from './product-set-details-detail.component';
import { ProductSetDetailsUpdateComponent } from './product-set-details-update.component';
import {
  ProductSetDetailsDeletePopupComponent,
  ProductSetDetailsDeleteDialogComponent
} from './product-set-details-delete-dialog.component';
import { productSetDetailsRoute, productSetDetailsPopupRoute } from './product-set-details.route';

const ENTITY_STATES = [...productSetDetailsRoute, ...productSetDetailsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductSetDetailsComponent,
    ProductSetDetailsDetailComponent,
    ProductSetDetailsUpdateComponent,
    ProductSetDetailsDeleteDialogComponent,
    ProductSetDetailsDeletePopupComponent
  ],
  entryComponents: [ProductSetDetailsDeleteDialogComponent]
})
export class ResourceProductSetDetailsModule {}
