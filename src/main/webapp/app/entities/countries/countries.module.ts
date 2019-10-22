import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { CountriesComponent } from './countries.component';
import { CountriesDetailComponent } from './countries-detail.component';
import { CountriesUpdateComponent } from './countries-update.component';
import { CountriesDeletePopupComponent, CountriesDeleteDialogComponent } from './countries-delete-dialog.component';
import { countriesRoute, countriesPopupRoute } from './countries.route';

const ENTITY_STATES = [...countriesRoute, ...countriesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CountriesComponent,
    CountriesDetailComponent,
    CountriesUpdateComponent,
    CountriesDeleteDialogComponent,
    CountriesDeletePopupComponent
  ],
  entryComponents: [CountriesDeleteDialogComponent]
})
export class ResourceCountriesModule {}
