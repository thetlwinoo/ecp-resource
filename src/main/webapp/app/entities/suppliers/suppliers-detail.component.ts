import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISuppliers } from 'app/shared/model/suppliers.model';

@Component({
  selector: 'jhi-suppliers-detail',
  templateUrl: './suppliers-detail.component.html'
})
export class SuppliersDetailComponent implements OnInit {
  suppliers: ISuppliers;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ suppliers }) => {
      this.suppliers = suppliers;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
