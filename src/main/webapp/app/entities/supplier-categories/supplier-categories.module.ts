import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { SupplierCategoriesComponent } from './supplier-categories.component';
import { SupplierCategoriesDetailComponent } from './supplier-categories-detail.component';
import { SupplierCategoriesUpdateComponent } from './supplier-categories-update.component';
import {
  SupplierCategoriesDeletePopupComponent,
  SupplierCategoriesDeleteDialogComponent
} from './supplier-categories-delete-dialog.component';
import { supplierCategoriesRoute, supplierCategoriesPopupRoute } from './supplier-categories.route';

const ENTITY_STATES = [...supplierCategoriesRoute, ...supplierCategoriesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SupplierCategoriesComponent,
    SupplierCategoriesDetailComponent,
    SupplierCategoriesUpdateComponent,
    SupplierCategoriesDeleteDialogComponent,
    SupplierCategoriesDeletePopupComponent
  ],
  entryComponents: [SupplierCategoriesDeleteDialogComponent]
})
export class ResourceSupplierCategoriesModule {}
