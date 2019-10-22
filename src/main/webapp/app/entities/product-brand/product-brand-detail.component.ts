import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductBrand } from 'app/shared/model/product-brand.model';

@Component({
  selector: 'jhi-product-brand-detail',
  templateUrl: './product-brand-detail.component.html'
})
export class ProductBrandDetailComponent implements OnInit {
  productBrand: IProductBrand;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productBrand }) => {
      this.productBrand = productBrand;
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
