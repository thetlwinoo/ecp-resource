import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResourceSharedModule } from 'app/shared/shared.module';
import { PeopleComponent } from './people.component';
import { PeopleDetailComponent } from './people-detail.component';
import { PeopleUpdateComponent } from './people-update.component';
import { PeopleDeletePopupComponent, PeopleDeleteDialogComponent } from './people-delete-dialog.component';
import { peopleRoute, peoplePopupRoute } from './people.route';

const ENTITY_STATES = [...peopleRoute, ...peoplePopupRoute];

@NgModule({
  imports: [ResourceSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PeopleComponent, PeopleDetailComponent, PeopleUpdateComponent, PeopleDeleteDialogComponent, PeopleDeletePopupComponent],
  entryComponents: [PeopleDeleteDialogComponent]
})
export class ResourcePeopleModule {}
