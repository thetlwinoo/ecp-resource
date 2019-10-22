import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { PackageTypesComponent } from './package-types.component';
import { PackageTypesDetailComponent } from './package-types-detail.component';
import { PackageTypesUpdateComponent } from './package-types-update.component';
import { PackageTypesDeletePopupComponent, PackageTypesDeleteDialogComponent } from './package-types-delete-dialog.component';
import { packageTypesRoute, packageTypesPopupRoute } from './package-types.route';

const ENTITY_STATES = [...packageTypesRoute, ...packageTypesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PackageTypesComponent,
    PackageTypesDetailComponent,
    PackageTypesUpdateComponent,
    PackageTypesDeleteDialogComponent,
    PackageTypesDeletePopupComponent
  ],
  entryComponents: [PackageTypesDeleteDialogComponent]
})
export class ResourcePackageTypesModule {}
