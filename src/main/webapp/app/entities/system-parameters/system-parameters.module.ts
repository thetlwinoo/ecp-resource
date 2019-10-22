import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { SystemParametersComponent } from './system-parameters.component';
import { SystemParametersDetailComponent } from './system-parameters-detail.component';
import { SystemParametersUpdateComponent } from './system-parameters-update.component';
import { SystemParametersDeletePopupComponent, SystemParametersDeleteDialogComponent } from './system-parameters-delete-dialog.component';
import { systemParametersRoute, systemParametersPopupRoute } from './system-parameters.route';

const ENTITY_STATES = [...systemParametersRoute, ...systemParametersPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SystemParametersComponent,
    SystemParametersDetailComponent,
    SystemParametersUpdateComponent,
    SystemParametersDeleteDialogComponent,
    SystemParametersDeletePopupComponent
  ],
  entryComponents: [SystemParametersDeleteDialogComponent]
})
export class ResourceSystemParametersModule {}
