import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { StateProvincesComponent } from './state-provinces.component';
import { StateProvincesDetailComponent } from './state-provinces-detail.component';
import { StateProvincesUpdateComponent } from './state-provinces-update.component';
import { StateProvincesDeletePopupComponent, StateProvincesDeleteDialogComponent } from './state-provinces-delete-dialog.component';
import { stateProvincesRoute, stateProvincesPopupRoute } from './state-provinces.route';

const ENTITY_STATES = [...stateProvincesRoute, ...stateProvincesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    StateProvincesComponent,
    StateProvincesDetailComponent,
    StateProvincesUpdateComponent,
    StateProvincesDeleteDialogComponent,
    StateProvincesDeletePopupComponent
  ],
  entryComponents: [StateProvincesDeleteDialogComponent]
})
export class ResourceStateProvincesModule {}
