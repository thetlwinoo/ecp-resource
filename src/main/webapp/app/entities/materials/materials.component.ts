import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IMaterials } from 'app/shared/model/materials.model';
import { AccountService } from 'app/core/auth/account.service';
import { MaterialsService } from './materials.service';

@Component({
  selector: 'jhi-materials',
  templateUrl: './materials.component.html'
})
export class MaterialsComponent implements OnInit, OnDestroy {
  materials: IMaterials[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected materialsService: MaterialsService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.materialsService
      .query()
      .pipe(
        filter((res: HttpResponse<IMaterials[]>) => res.ok),
        map((res: HttpResponse<IMaterials[]>) => res.body)
      )
      .subscribe((res: IMaterials[]) => {
        this.materials = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMaterials();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMaterials) {
    return item.id;
  }

  registerChangeInMaterials() {
    this.eventSubscriber = this.eventManager.subscribe('materialsListModification', response => this.loadAll());
  }
}
