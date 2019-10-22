import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { UnitMeasureComponent } from './unit-measure.component';
import { UnitMeasureDetailComponent } from './unit-measure-detail.component';
import { UnitMeasureUpdateComponent } from './unit-measure-update.component';
import { UnitMeasureDeletePopupComponent, UnitMeasureDeleteDialogComponent } from './unit-measure-delete-dialog.component';
import { unitMeasureRoute, unitMeasurePopupRoute } from './unit-measure.route';

const ENTITY_STATES = [...unitMeasureRoute, ...unitMeasurePopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UnitMeasureComponent,
    UnitMeasureDetailComponent,
    UnitMeasureUpdateComponent,
    UnitMeasureDeleteDialogComponent,
    UnitMeasureDeletePopupComponent
  ],
  entryComponents: [UnitMeasureDeleteDialogComponent]
})
export class ResourceUnitMeasureModule {}
