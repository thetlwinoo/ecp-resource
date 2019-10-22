import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ShoppingCartsComponent } from './shopping-carts.component';
import { ShoppingCartsDetailComponent } from './shopping-carts-detail.component';
import { ShoppingCartsUpdateComponent } from './shopping-carts-update.component';
import { ShoppingCartsDeletePopupComponent, ShoppingCartsDeleteDialogComponent } from './shopping-carts-delete-dialog.component';
import { shoppingCartsRoute, shoppingCartsPopupRoute } from './shopping-carts.route';

const ENTITY_STATES = [...shoppingCartsRoute, ...shoppingCartsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ShoppingCartsComponent,
    ShoppingCartsDetailComponent,
    ShoppingCartsUpdateComponent,
    ShoppingCartsDeleteDialogComponent,
    ShoppingCartsDeletePopupComponent
  ],
  entryComponents: [ShoppingCartsDeleteDialogComponent]
})
export class ResourceShoppingCartsModule {}
