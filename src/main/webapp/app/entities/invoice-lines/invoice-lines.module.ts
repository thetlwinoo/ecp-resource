import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { InvoiceLinesComponent } from './invoice-lines.component';
import { InvoiceLinesDetailComponent } from './invoice-lines-detail.component';
import { InvoiceLinesUpdateComponent } from './invoice-lines-update.component';
import { InvoiceLinesDeletePopupComponent, InvoiceLinesDeleteDialogComponent } from './invoice-lines-delete-dialog.component';
import { invoiceLinesRoute, invoiceLinesPopupRoute } from './invoice-lines.route';

const ENTITY_STATES = [...invoiceLinesRoute, ...invoiceLinesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InvoiceLinesComponent,
    InvoiceLinesDetailComponent,
    InvoiceLinesUpdateComponent,
    InvoiceLinesDeleteDialogComponent,
    InvoiceLinesDeletePopupComponent
  ],
  entryComponents: [InvoiceLinesDeleteDialogComponent]
})
export class ResourceInvoiceLinesModule {}
