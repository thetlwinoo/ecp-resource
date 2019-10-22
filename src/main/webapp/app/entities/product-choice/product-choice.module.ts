import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductChoiceComponent } from './product-choice.component';
import { ProductChoiceDetailComponent } from './product-choice-detail.component';
import { ProductChoiceUpdateComponent } from './product-choice-update.component';
import { ProductChoiceDeletePopupComponent, ProductChoiceDeleteDialogComponent } from './product-choice-delete-dialog.component';
import { productChoiceRoute, productChoicePopupRoute } from './product-choice.route';

const ENTITY_STATES = [...productChoiceRoute, ...productChoicePopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductChoiceComponent,
    ProductChoiceDetailComponent,
    ProductChoiceUpdateComponent,
    ProductChoiceDeleteDialogComponent,
    ProductChoiceDeletePopupComponent
  ],
  entryComponents: [ProductChoiceDeleteDialogComponent]
})
export class ResourceProductChoiceModule {}
