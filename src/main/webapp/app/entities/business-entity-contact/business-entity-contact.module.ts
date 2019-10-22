import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { BusinessEntityContactComponent } from './business-entity-contact.component';
import { BusinessEntityContactDetailComponent } from './business-entity-contact-detail.component';
import { BusinessEntityContactUpdateComponent } from './business-entity-contact-update.component';
import {
  BusinessEntityContactDeletePopupComponent,
  BusinessEntityContactDeleteDialogComponent
} from './business-entity-contact-delete-dialog.component';
import { businessEntityContactRoute, businessEntityContactPopupRoute } from './business-entity-contact.route';

const ENTITY_STATES = [...businessEntityContactRoute, ...businessEntityContactPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BusinessEntityContactComponent,
    BusinessEntityContactDetailComponent,
    BusinessEntityContactUpdateComponent,
    BusinessEntityContactDeleteDialogComponent,
    BusinessEntityContactDeletePopupComponent
  ],
  entryComponents: [BusinessEntityContactDeleteDialogComponent]
})
export class ResourceBusinessEntityContactModule {}
