import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ComparesComponent } from './compares.component';
import { ComparesDetailComponent } from './compares-detail.component';
import { ComparesUpdateComponent } from './compares-update.component';
import { ComparesDeletePopupComponent, ComparesDeleteDialogComponent } from './compares-delete-dialog.component';
import { comparesRoute, comparesPopupRoute } from './compares.route';

const ENTITY_STATES = [...comparesRoute, ...comparesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ComparesComponent,
    ComparesDetailComponent,
    ComparesUpdateComponent,
    ComparesDeleteDialogComponent,
    ComparesDeletePopupComponent
  ],
  entryComponents: [ComparesDeleteDialogComponent]
})
export class ResourceComparesModule {}
