import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { WarrantyTypesComponent } from './warranty-types.component';
import { WarrantyTypesDetailComponent } from './warranty-types-detail.component';
import { WarrantyTypesUpdateComponent } from './warranty-types-update.component';
import { WarrantyTypesDeletePopupComponent, WarrantyTypesDeleteDialogComponent } from './warranty-types-delete-dialog.component';
import { warrantyTypesRoute, warrantyTypesPopupRoute } from './warranty-types.route';

const ENTITY_STATES = [...warrantyTypesRoute, ...warrantyTypesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    WarrantyTypesComponent,
    WarrantyTypesDetailComponent,
    WarrantyTypesUpdateComponent,
    WarrantyTypesDeleteDialogComponent,
    WarrantyTypesDeletePopupComponent
  ],
  entryComponents: [WarrantyTypesDeleteDialogComponent]
})
export class ResourceWarrantyTypesModule {}
