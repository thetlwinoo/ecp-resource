import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPeople } from 'app/shared/model/people.model';
import { AccountService } from 'app/core/auth/account.service';
import { PeopleService } from './people.service';

@Component({
  selector: 'jhi-people',
  templateUrl: './people.component.html'
})
export class PeopleComponent implements OnInit, OnDestroy {
  people: IPeople[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected peopleService: PeopleService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.peopleService
      .query()
      .pipe(
        filter((res: HttpResponse<IPeople[]>) => res.ok),
        map((res: HttpResponse<IPeople[]>) => res.body)
      )
      .subscribe((res: IPeople[]) => {
        this.people = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPeople();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPeople) {
    return item.id;
  }

  registerChangeInPeople() {
    this.eventSubscriber = this.eventManager.subscribe('peopleListModification', response => this.loadAll());
  }
}
