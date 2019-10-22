import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { InvoicesComponent } from './invoices.component';
import { InvoicesDetailComponent } from './invoices-detail.component';
import { InvoicesUpdateComponent } from './invoices-update.component';
import { InvoicesDeletePopupComponent, InvoicesDeleteDialogComponent } from './invoices-delete-dialog.component';
import { invoicesRoute, invoicesPopupRoute } from './invoices.route';

const ENTITY_STATES = [...invoicesRoute, ...invoicesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InvoicesComponent,
    InvoicesDetailComponent,
    InvoicesUpdateComponent,
    InvoicesDeleteDialogComponent,
    InvoicesDeletePopupComponent
  ],
  entryComponents: [InvoicesDeleteDialogComponent]
})
export class ResourceInvoicesModule {}
