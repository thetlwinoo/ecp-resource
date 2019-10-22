import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ProductDocumentComponent } from './product-document.component';
import { ProductDocumentDetailComponent } from './product-document-detail.component';
import { ProductDocumentUpdateComponent } from './product-document-update.component';
import { ProductDocumentDeletePopupComponent, ProductDocumentDeleteDialogComponent } from './product-document-delete-dialog.component';
import { productDocumentRoute, productDocumentPopupRoute } from './product-document.route';

const ENTITY_STATES = [...productDocumentRoute, ...productDocumentPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductDocumentComponent,
    ProductDocumentDetailComponent,
    ProductDocumentUpdateComponent,
    ProductDocumentDeleteDialogComponent,
    ProductDocumentDeletePopupComponent
  ],
  entryComponents: [ProductDocumentDeleteDialogComponent]
})
export class ResourceProductDocumentModule {}
