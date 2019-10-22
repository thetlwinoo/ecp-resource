import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { CustomersComponent } from './customers.component';
import { CustomersDetailComponent } from './customers-detail.component';
import { CustomersUpdateComponent } from './customers-update.component';
import { CustomersDeletePopupComponent, CustomersDeleteDialogComponent } from './customers-delete-dialog.component';
import { customersRoute, customersPopupRoute } from './customers.route';

const ENTITY_STATES = [...customersRoute, ...customersPopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CustomersComponent,
    CustomersDetailComponent,
    CustomersUpdateComponent,
    CustomersDeleteDialogComponent,
    CustomersDeletePopupComponent
  ],
  entryComponents: [CustomersDeleteDialogComponent]
})
export class ResourceCustomersModule {}
