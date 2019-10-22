import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { WishlistProductsComponent } from './wishlist-products.component';
import { WishlistProductsDetailComponent } from './wishlist-products-detail.component';
import { WishlistProductsUpdateComponent } from './wishlist-products-update.component';
import { WishlistProductsDeletePopupComponent, WishlistProductsDeleteDialogComponent } from './wishlist-products-delete-dialog.component';
import { wishlistProductsRoute, wishlistProductsPopupRoute } from './wishlist-products.route';

const ENTITY_STATES = [...wishlistProductsRoute, ...wishlistProductsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    WishlistProductsComponent,
    WishlistProductsDetailComponent,
    WishlistProductsUpdateComponent,
    WishlistProductsDeleteDialogComponent,
    WishlistProductsDeletePopupComponent
  ],
  entryComponents: [WishlistProductsDeleteDialogComponent]
})
export class ResourceWishlistProductsModule {}
