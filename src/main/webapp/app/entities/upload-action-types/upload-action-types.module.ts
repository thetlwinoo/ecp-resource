import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { UploadActionTypesComponent } from './upload-action-types.component';
import { UploadActionTypesDetailComponent } from './upload-action-types-detail.component';
import { UploadActionTypesUpdateComponent } from './upload-action-types-update.component';
import {
  UploadActionTypesDeletePopupComponent,
  UploadActionTypesDeleteDialogComponent
} from './upload-action-types-delete-dialog.component';
import { uploadActionTypesRoute, uploadActionTypesPopupRoute } from './upload-action-types.route';

const ENTITY_STATES = [...uploadActionTypesRoute, ...uploadActionTypesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UploadActionTypesComponent,
    UploadActionTypesDetailComponent,
    UploadActionTypesUpdateComponent,
    UploadActionTypesDeleteDialogComponent,
    UploadActionTypesDeletePopupComponent
  ],
  entryComponents: [UploadActionTypesDeleteDialogComponent]
})
export class ResourceUploadActionTypesModule {}
