import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IUnitMeasure } from 'app/shared/model/unit-measure.model';
import { AccountService } from 'app/core/auth/account.service';
import { UnitMeasureService } from './unit-measure.service';

@Component({
  selector: 'jhi-unit-measure',
  templateUrl: './unit-measure.component.html'
})
export class UnitMeasureComponent implements OnInit, OnDestroy {
  unitMeasures: IUnitMeasure[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected unitMeasureService: UnitMeasureService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.unitMeasureService
      .query()
      .pipe(
        filter((res: HttpResponse<IUnitMeasure[]>) => res.ok),
        map((res: HttpResponse<IUnitMeasure[]>) => res.body)
      )
      .subscribe((res: IUnitMeasure[]) => {
        this.unitMeasures = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUnitMeasures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUnitMeasure) {
    return item.id;
  }

  registerChangeInUnitMeasures() {
    this.eventSubscriber = this.eventManager.subscribe('unitMeasureListModification', response => this.loadAll());
  }
}
