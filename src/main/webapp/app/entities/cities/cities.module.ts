import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { CitiesComponent } from './cities.component';
import { CitiesDetailComponent } from './cities-detail.component';
import { CitiesUpdateComponent } from './cities-update.component';
import { CitiesDeletePopupComponent, CitiesDeleteDialogComponent } from './cities-delete-dialog.component';
import { citiesRoute, citiesPopupRoute } from './cities.route';

const ENTITY_STATES = [...citiesRoute, ...citiesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CitiesComponent, CitiesDetailComponent, CitiesUpdateComponent, CitiesDeleteDialogComponent, CitiesDeletePopupComponent],
  entryComponents: [CitiesDeleteDialogComponent]
})
export class ResourceCitiesModule {}
