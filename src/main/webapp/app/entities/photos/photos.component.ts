import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IPhotos } from 'app/shared/model/photos.model';
import { AccountService } from 'app/core/auth/account.service';
import { PhotosService } from './photos.service';

@Component({
  selector: 'jhi-photos',
  templateUrl: './photos.component.html'
})
export class PhotosComponent implements OnInit, OnDestroy {
  photos: IPhotos[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected photosService: PhotosService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.photosService
      .query()
      .pipe(
        filter((res: HttpResponse<IPhotos[]>) => res.ok),
        map((res: HttpResponse<IPhotos[]>) => res.body)
      )
      .subscribe((res: IPhotos[]) => {
        this.photos = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPhotos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPhotos) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInPhotos() {
    this.eventSubscriber = this.eventManager.subscribe('photosListModification', response => this.loadAll());
  }
}
