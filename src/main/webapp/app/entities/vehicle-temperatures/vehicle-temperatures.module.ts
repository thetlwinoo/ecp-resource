import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { VehicleTemperaturesComponent } from './vehicle-temperatures.component';
import { VehicleTemperaturesDetailComponent } from './vehicle-temperatures-detail.component';
import { VehicleTemperaturesUpdateComponent } from './vehicle-temperatures-update.component';
import {
  VehicleTemperaturesDeletePopupComponent,
  VehicleTemperaturesDeleteDialogComponent
} from './vehicle-temperatures-delete-dialog.component';
import { vehicleTemperaturesRoute, vehicleTemperaturesPopupRoute } from './vehicle-temperatures.route';

const ENTITY_STATES = [...vehicleTemperaturesRoute, ...vehicleTemperaturesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VehicleTemperaturesComponent,
    VehicleTemperaturesDetailComponent,
    VehicleTemperaturesUpdateComponent,
    VehicleTemperaturesDeleteDialogComponent,
    VehicleTemperaturesDeletePopupComponent
  ],
  entryComponents: [VehicleTemperaturesDeleteDialogComponent]
})
export class ResourceVehicleTemperaturesModule {}
