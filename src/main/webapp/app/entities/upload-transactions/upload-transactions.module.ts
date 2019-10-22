import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { UploadTransactionsComponent } from './upload-transactions.component';
import { UploadTransactionsDetailComponent } from './upload-transactions-detail.component';
import { UploadTransactionsUpdateComponent } from './upload-transactions-update.component';
import {
  UploadTransactionsDeletePopupComponent,
  UploadTransactionsDeleteDialogComponent
} from './upload-transactions-delete-dialog.component';
import { uploadTransactionsRoute, uploadTransactionsPopupRoute } from './upload-transactions.route';

const ENTITY_STATES = [...uploadTransactionsRoute, ...uploadTransactionsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UploadTransactionsComponent,
    UploadTransactionsDetailComponent,
    UploadTransactionsUpdateComponent,
    UploadTransactionsDeleteDialogComponent,
    UploadTransactionsDeletePopupComponent
  ],
  entryComponents: [UploadTransactionsDeleteDialogComponent]
})
export class ResourceUploadTransactionsModule {}
