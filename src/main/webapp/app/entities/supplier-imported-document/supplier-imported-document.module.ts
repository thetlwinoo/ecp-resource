import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { SupplierImportedDocumentComponent } from './supplier-imported-document.component';
import { SupplierImportedDocumentDetailComponent } from './supplier-imported-document-detail.component';
import { SupplierImportedDocumentUpdateComponent } from './supplier-imported-document-update.component';
import {
  SupplierImportedDocumentDeletePopupComponent,
  SupplierImportedDocumentDeleteDialogComponent
} from './supplier-imported-document-delete-dialog.component';
import { supplierImportedDocumentRoute, supplierImportedDocumentPopupRoute } from './supplier-imported-document.route';

const ENTITY_STATES = [...supplierImportedDocumentRoute, ...supplierImportedDocumentPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SupplierImportedDocumentComponent,
    SupplierImportedDocumentDetailComponent,
    SupplierImportedDocumentUpdateComponent,
    SupplierImportedDocumentDeleteDialogComponent,
    SupplierImportedDocumentDeletePopupComponent
  ],
  entryComponents: [SupplierImportedDocumentDeleteDialogComponent]
})
export class ResourceSupplierImportedDocumentModule {}
