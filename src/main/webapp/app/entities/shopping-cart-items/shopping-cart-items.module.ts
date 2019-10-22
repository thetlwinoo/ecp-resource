import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ShoppingCartItemsComponent } from './shopping-cart-items.component';
import { ShoppingCartItemsDetailComponent } from './shopping-cart-items-detail.component';
import { ShoppingCartItemsUpdateComponent } from './shopping-cart-items-update.component';
import {
  ShoppingCartItemsDeletePopupComponent,
  ShoppingCartItemsDeleteDialogComponent
} from './shopping-cart-items-delete-dialog.component';
import { shoppingCartItemsRoute, shoppingCartItemsPopupRoute } from './shopping-cart-items.route';

const ENTITY_STATES = [...shoppingCartItemsRoute, ...shoppingCartItemsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ShoppingCartItemsComponent,
    ShoppingCartItemsDetailComponent,
    ShoppingCartItemsUpdateComponent,
    ShoppingCartItemsDeleteDialogComponent,
    ShoppingCartItemsDeletePopupComponent
  ],
  entryComponents: [ShoppingCartItemsDeleteDialogComponent]
})
export class ResourceShoppingCartItemsModule {}
