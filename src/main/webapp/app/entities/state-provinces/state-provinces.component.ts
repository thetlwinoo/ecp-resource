import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IStateProvinces } from 'app/shared/model/state-provinces.model';
import { AccountService } from 'app/core/auth/account.service';
import { StateProvincesService } from './state-provinces.service';

@Component({
  selector: 'jhi-state-provinces',
  templateUrl: './state-provinces.component.html'
})
export class StateProvincesComponent implements OnInit, OnDestroy {
  stateProvinces: IStateProvinces[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected stateProvincesService: StateProvincesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.stateProvincesService
      .query()
      .pipe(
        filter((res: HttpResponse<IStateProvinces[]>) => res.ok),
        map((res: HttpResponse<IStateProvinces[]>) => res.body)
      )
      .subscribe((res: IStateProvinces[]) => {
        this.stateProvinces = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInStateProvinces();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IStateProvinces) {
    return item.id;
  }

  registerChangeInStateProvinces() {
    this.eventSubscriber = this.eventManager.subscribe('stateProvincesListModification', response => this.loadAll());
  }
}
