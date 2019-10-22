import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { DangerousGoodsComponent } from './dangerous-goods.component';
import { DangerousGoodsDetailComponent } from './dangerous-goods-detail.component';
import { DangerousGoodsUpdateComponent } from './dangerous-goods-update.component';
import { DangerousGoodsDeletePopupComponent, DangerousGoodsDeleteDialogComponent } from './dangerous-goods-delete-dialog.component';
import { dangerousGoodsRoute, dangerousGoodsPopupRoute } from './dangerous-goods.route';

const ENTITY_STATES = [...dangerousGoodsRoute, ...dangerousGoodsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DangerousGoodsComponent,
    DangerousGoodsDetailComponent,
    DangerousGoodsUpdateComponent,
    DangerousGoodsDeleteDialogComponent,
    DangerousGoodsDeletePopupComponent
  ],
  entryComponents: [DangerousGoodsDeleteDialogComponent]
})
export class ResourceDangerousGoodsModule {}
