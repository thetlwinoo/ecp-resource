import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IColdRoomTemperatures } from 'app/shared/model/cold-room-temperatures.model';
import { AccountService } from 'app/core/auth/account.service';
import { ColdRoomTemperaturesService } from './cold-room-temperatures.service';

@Component({
  selector: 'jhi-cold-room-temperatures',
  templateUrl: './cold-room-temperatures.component.html'
})
export class ColdRoomTemperaturesComponent implements OnInit, OnDestroy {
  coldRoomTemperatures: IColdRoomTemperatures[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected coldRoomTemperaturesService: ColdRoomTemperaturesService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.coldRoomTemperaturesService
      .query()
      .pipe(
        filter((res: HttpResponse<IColdRoomTemperatures[]>) => res.ok),
        map((res: HttpResponse<IColdRoomTemperatures[]>) => res.body)
      )
      .subscribe((res: IColdRoomTemperatures[]) => {
        this.coldRoomTemperatures = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInColdRoomTemperatures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IColdRoomTemperatures) {
    return item.id;
  }

  registerChangeInColdRoomTemperatures() {
    this.eventSubscriber = this.eventManager.subscribe('coldRoomTemperaturesListModification', response => this.loadAll());
  }
}
