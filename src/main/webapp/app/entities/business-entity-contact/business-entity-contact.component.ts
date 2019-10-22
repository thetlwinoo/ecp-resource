import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessEntityContact } from 'app/shared/model/business-entity-contact.model';
import { AccountService } from 'app/core/auth/account.service';
import { BusinessEntityContactService } from './business-entity-contact.service';

@Component({
  selector: 'jhi-business-entity-contact',
  templateUrl: './business-entity-contact.component.html'
})
export class BusinessEntityContactComponent implements OnInit, OnDestroy {
  businessEntityContacts: IBusinessEntityContact[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected businessEntityContactService: BusinessEntityContactService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.businessEntityContactService
      .query()
      .pipe(
        filter((res: HttpResponse<IBusinessEntityContact[]>) => res.ok),
        map((res: HttpResponse<IBusinessEntityContact[]>) => res.body)
      )
      .subscribe((res: IBusinessEntityContact[]) => {
        this.businessEntityContacts = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBusinessEntityContacts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBusinessEntityContact) {
    return item.id;
  }

  registerChangeInBusinessEntityContacts() {
    this.eventSubscriber = this.eventManager.subscribe('businessEntityContactListModification', response => this.loadAll());
  }
}
