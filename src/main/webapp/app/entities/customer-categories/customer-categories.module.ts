import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { CustomerCategoriesComponent } from './customer-categories.component';
import { CustomerCategoriesDetailComponent } from './customer-categories-detail.component';
import { CustomerCategoriesUpdateComponent } from './customer-categories-update.component';
import {
  CustomerCategoriesDeletePopupComponent,
  CustomerCategoriesDeleteDialogComponent
} from './customer-categories-delete-dialog.component';
import { customerCategoriesRoute, customerCategoriesPopupRoute } from './customer-categories.route';

const ENTITY_STATES = [...customerCategoriesRoute, ...customerCategoriesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CustomerCategoriesComponent,
    CustomerCategoriesDetailComponent,
    CustomerCategoriesUpdateComponent,
    CustomerCategoriesDeleteDialogComponent,
    CustomerCategoriesDeletePopupComponent
  ],
  entryComponents: [CustomerCategoriesDeleteDialogComponent]
})
export class ResourceCustomerCategoriesModule {}
