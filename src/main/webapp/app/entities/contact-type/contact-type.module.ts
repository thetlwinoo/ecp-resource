import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { ContactTypeComponent } from './contact-type.component';
import { ContactTypeDetailComponent } from './contact-type-detail.component';
import { ContactTypeUpdateComponent } from './contact-type-update.component';
import { ContactTypeDeletePopupComponent, ContactTypeDeleteDialogComponent } from './contact-type-delete-dialog.component';
import { contactTypeRoute, contactTypePopupRoute } from './contact-type.route';

const ENTITY_STATES = [...contactTypeRoute, ...contactTypePopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ContactTypeComponent,
    ContactTypeDetailComponent,
    ContactTypeUpdateComponent,
    ContactTypeDeleteDialogComponent,
    ContactTypeDeletePopupComponent
  ],
  entryComponents: [ContactTypeDeleteDialogComponent]
})
export class ResourceContactTypeModule {}
