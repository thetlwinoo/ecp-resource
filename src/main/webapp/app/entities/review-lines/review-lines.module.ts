import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ReviewLinesComponent } from './review-lines.component';
import { ReviewLinesDetailComponent } from './review-lines-detail.component';
import { ReviewLinesUpdateComponent } from './review-lines-update.component';
import { ReviewLinesDeletePopupComponent, ReviewLinesDeleteDialogComponent } from './review-lines-delete-dialog.component';
import { reviewLinesRoute, reviewLinesPopupRoute } from './review-lines.route';

const ENTITY_STATES = [...reviewLinesRoute, ...reviewLinesPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReviewLinesComponent,
    ReviewLinesDetailComponent,
    ReviewLinesUpdateComponent,
    ReviewLinesDeleteDialogComponent,
    ReviewLinesDeletePopupComponent
  ],
  entryComponents: [ReviewLinesDeleteDialogComponent]
})
export class ResourceReviewLinesModule {}
