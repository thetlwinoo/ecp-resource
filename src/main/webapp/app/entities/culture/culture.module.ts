import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { CultureComponent } from './culture.component';
import { CultureDetailComponent } from './culture-detail.component';
import { CultureUpdateComponent } from './culture-update.component';
import { CultureDeletePopupComponent, CultureDeleteDialogComponent } from './culture-delete-dialog.component';
import { cultureRoute, culturePopupRoute } from './culture.route';

const ENTITY_STATES = [...cultureRoute, ...culturePopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CultureComponent,
    CultureDetailComponent,
    CultureUpdateComponent,
    CultureDeleteDialogComponent,
    CultureDeletePopupComponent
  ],
  entryComponents: [CultureDeleteDialogComponent]
})
export class ResourceCultureModule {}
