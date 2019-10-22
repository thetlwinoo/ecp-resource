import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ColdRoomTemperaturesComponent } from './cold-room-temperatures.component';
import { ColdRoomTemperaturesDetailComponent } from './cold-room-temperatures-detail.component';
import { ColdRoomTemperaturesUpdateComponent } from './cold-room-temperatures-update.component';
import {
  ColdRoomTemperaturesDeletePopupComponent,
  ColdRoomTemperaturesDeleteDialogComponent
} from './cold-room-temperatures-delete-dialog.component';
import { coldRoomTemperaturesRoute, coldRoomTemperaturesPopupRoute } from './cold-room-temperatures.route';

const ENTITY_STATES = [...coldRoomTemperaturesRoute, ...coldRoomTemperaturesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ColdRoomTemperaturesComponent,
    ColdRoomTemperaturesDetailComponent,
    ColdRoomTemperaturesUpdateComponent,
    ColdRoomTemperaturesDeleteDialogComponent,
    ColdRoomTemperaturesDeletePopupComponent
  ],
  entryComponents: [ColdRoomTemperaturesDeleteDialogComponent]
})
export class ResourceColdRoomTemperaturesModule {}
