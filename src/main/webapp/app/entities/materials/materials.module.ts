import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { MaterialsComponent } from './materials.component';
import { MaterialsDetailComponent } from './materials-detail.component';
import { MaterialsUpdateComponent } from './materials-update.component';
import { MaterialsDeletePopupComponent, MaterialsDeleteDialogComponent } from './materials-delete-dialog.component';
import { materialsRoute, materialsPopupRoute } from './materials.route';

const ENTITY_STATES = [...materialsRoute, ...materialsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MaterialsComponent,
    MaterialsDetailComponent,
    MaterialsUpdateComponent,
    MaterialsDeleteDialogComponent,
    MaterialsDeletePopupComponent
  ],
  entryComponents: [MaterialsDeleteDialogComponent]
})
export class ResourceMaterialsModule {}
