import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ReviewsComponent } from './reviews.component';
import { ReviewsDetailComponent } from './reviews-detail.component';
import { ReviewsUpdateComponent } from './reviews-update.component';
import { ReviewsDeletePopupComponent, ReviewsDeleteDialogComponent } from './reviews-delete-dialog.component';
import { reviewsRoute, reviewsPopupRoute } from './reviews.route';

const ENTITY_STATES = [...reviewsRoute, ...reviewsPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReviewsComponent,
    ReviewsDetailComponent,
    ReviewsUpdateComponent,
    ReviewsDeleteDialogComponent,
    ReviewsDeletePopupComponent
  ],
  entryComponents: [ReviewsDeleteDialogComponent]
})
export class ResourceReviewsModule {}
