import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address-types',
        loadChildren: () => import('./address-types/address-types.module').then(m => m.ResourceAddressTypesModule)
      },
      {
        path: 'culture',
        loadChildren: () => import('./culture/culture.module').then(m => m.ResourceCultureModule)
      },
      {
        path: 'addresses',
        loadChildren: () => import('./addresses/addresses.module').then(m => m.ResourceAddressesModule)
      },
      {
        path: 'business-entity-address',
        loadChildren: () =>
          import('./business-entity-address/business-entity-address.module').then(m => m.ResourceBusinessEntityAddressModule)
      },
      {
        path: 'ship-method',
        loadChildren: () => import('./ship-method/ship-method.module').then(m => m.ResourceShipMethodModule)
      },
      {
        path: 'person-email-address',
        loadChildren: () => import('./person-email-address/person-email-address.module').then(m => m.ResourcePersonEmailAddressModule)
      },
      {
        path: 'person-phone',
        loadChildren: () => import('./person-phone/person-phone.module').then(m => m.ResourcePersonPhoneModule)
      },
      {
        path: 'phone-number-type',
        loadChildren: () => import('./phone-number-type/phone-number-type.module').then(m => m.ResourcePhoneNumberTypeModule)
      },
      {
        path: 'contact-type',
        loadChildren: () => import('./contact-type/contact-type.module').then(m => m.ResourceContactTypeModule)
      },
      {
        path: 'business-entity-contact',
        loadChildren: () =>
          import('./business-entity-contact/business-entity-contact.module').then(m => m.ResourceBusinessEntityContactModule)
      },
      {
        path: 'countries',
        loadChildren: () => import('./countries/countries.module').then(m => m.ResourceCountriesModule)
      },
      {
        path: 'state-provinces',
        loadChildren: () => import('./state-provinces/state-provinces.module').then(m => m.ResourceStateProvincesModule)
      },
      {
        path: 'cities',
        loadChildren: () => import('./cities/cities.module').then(m => m.ResourceCitiesModule)
      },
      {
        path: 'system-parameters',
        loadChildren: () => import('./system-parameters/system-parameters.module').then(m => m.ResourceSystemParametersModule)
      },
      {
        path: 'transaction-types',
        loadChildren: () => import('./transaction-types/transaction-types.module').then(m => m.ResourceTransactionTypesModule)
      },
      {
        path: 'people',
        loadChildren: () => import('./people/people.module').then(m => m.ResourcePeopleModule)
      },
      {
        path: 'payment-methods',
        loadChildren: () => import('./payment-methods/payment-methods.module').then(m => m.ResourcePaymentMethodsModule)
      },
      {
        path: 'delivery-methods',
        loadChildren: () => import('./delivery-methods/delivery-methods.module').then(m => m.ResourceDeliveryMethodsModule)
      },
      {
        path: 'supplier-categories',
        loadChildren: () => import('./supplier-categories/supplier-categories.module').then(m => m.ResourceSupplierCategoriesModule)
      },
      {
        path: 'suppliers',
        loadChildren: () => import('./suppliers/suppliers.module').then(m => m.ResourceSuppliersModule)
      },
      {
        path: 'supplier-transactions',
        loadChildren: () => import('./supplier-transactions/supplier-transactions.module').then(m => m.ResourceSupplierTransactionsModule)
      },
      {
        path: 'currency-rate',
        loadChildren: () => import('./currency-rate/currency-rate.module').then(m => m.ResourceCurrencyRateModule)
      },
      {
        path: 'purchase-orders',
        loadChildren: () => import('./purchase-orders/purchase-orders.module').then(m => m.ResourcePurchaseOrdersModule)
      },
      {
        path: 'purchase-order-lines',
        loadChildren: () => import('./purchase-order-lines/purchase-order-lines.module').then(m => m.ResourcePurchaseOrderLinesModule)
      },
      {
        path: 'buying-groups',
        loadChildren: () => import('./buying-groups/buying-groups.module').then(m => m.ResourceBuyingGroupsModule)
      },
      {
        path: 'customer-categories',
        loadChildren: () => import('./customer-categories/customer-categories.module').then(m => m.ResourceCustomerCategoriesModule)
      },
      {
        path: 'customers',
        loadChildren: () => import('./customers/customers.module').then(m => m.ResourceCustomersModule)
      },
      {
        path: 'customer-transactions',
        loadChildren: () => import('./customer-transactions/customer-transactions.module').then(m => m.ResourceCustomerTransactionsModule)
      },
      {
        path: 'payment-transactions',
        loadChildren: () => import('./payment-transactions/payment-transactions.module').then(m => m.ResourcePaymentTransactionsModule)
      },
      {
        path: 'invoice-lines',
        loadChildren: () => import('./invoice-lines/invoice-lines.module').then(m => m.ResourceInvoiceLinesModule)
      },
      {
        path: 'invoices',
        loadChildren: () => import('./invoices/invoices.module').then(m => m.ResourceInvoicesModule)
      },
      {
        path: 'order-lines',
        loadChildren: () => import('./order-lines/order-lines.module').then(m => m.ResourceOrderLinesModule)
      },
      {
        path: 'orders',
        loadChildren: () => import('./orders/orders.module').then(m => m.ResourceOrdersModule)
      },
      {
        path: 'special-deals',
        loadChildren: () => import('./special-deals/special-deals.module').then(m => m.ResourceSpecialDealsModule)
      },
      {
        path: 'cold-room-temperatures',
        loadChildren: () => import('./cold-room-temperatures/cold-room-temperatures.module').then(m => m.ResourceColdRoomTemperaturesModule)
      },
      {
        path: 'package-types',
        loadChildren: () => import('./package-types/package-types.module').then(m => m.ResourcePackageTypesModule)
      },
      {
        path: 'products',
        loadChildren: () => import('./products/products.module').then(m => m.ResourceProductsModule)
      },
      {
        path: 'barcode-types',
        loadChildren: () => import('./barcode-types/barcode-types.module').then(m => m.ResourceBarcodeTypesModule)
      },
      {
        path: 'stock-items',
        loadChildren: () => import('./stock-items/stock-items.module').then(m => m.ResourceStockItemsModule)
      },
      {
        path: 'stock-item-temp',
        loadChildren: () => import('./stock-item-temp/stock-item-temp.module').then(m => m.ResourceStockItemTempModule)
      },
      {
        path: 'upload-transactions',
        loadChildren: () => import('./upload-transactions/upload-transactions.module').then(m => m.ResourceUploadTransactionsModule)
      },
      {
        path: 'upload-action-types',
        loadChildren: () => import('./upload-action-types/upload-action-types.module').then(m => m.ResourceUploadActionTypesModule)
      },
      {
        path: 'stock-item-transactions',
        loadChildren: () =>
          import('./stock-item-transactions/stock-item-transactions.module').then(m => m.ResourceStockItemTransactionsModule)
      },
      {
        path: 'stock-item-holdings',
        loadChildren: () => import('./stock-item-holdings/stock-item-holdings.module').then(m => m.ResourceStockItemHoldingsModule)
      },
      {
        path: 'warranty-types',
        loadChildren: () => import('./warranty-types/warranty-types.module').then(m => m.ResourceWarrantyTypesModule)
      },
      {
        path: 'product-attribute',
        loadChildren: () => import('./product-attribute/product-attribute.module').then(m => m.ResourceProductAttributeModule)
      },
      {
        path: 'product-attribute-set',
        loadChildren: () => import('./product-attribute-set/product-attribute-set.module').then(m => m.ResourceProductAttributeSetModule)
      },
      {
        path: 'product-option',
        loadChildren: () => import('./product-option/product-option.module').then(m => m.ResourceProductOptionModule)
      },
      {
        path: 'product-option-set',
        loadChildren: () => import('./product-option-set/product-option-set.module').then(m => m.ResourceProductOptionSetModule)
      },
      {
        path: 'product-choice',
        loadChildren: () => import('./product-choice/product-choice.module').then(m => m.ResourceProductChoiceModule)
      },
      {
        path: 'product-set',
        loadChildren: () => import('./product-set/product-set.module').then(m => m.ResourceProductSetModule)
      },
      {
        path: 'product-set-details',
        loadChildren: () => import('./product-set-details/product-set-details.module').then(m => m.ResourceProductSetDetailsModule)
      },
      {
        path: 'product-document',
        loadChildren: () => import('./product-document/product-document.module').then(m => m.ResourceProductDocumentModule)
      },
      {
        path: 'materials',
        loadChildren: () => import('./materials/materials.module').then(m => m.ResourceMaterialsModule)
      },
      {
        path: 'dangerous-goods',
        loadChildren: () => import('./dangerous-goods/dangerous-goods.module').then(m => m.ResourceDangerousGoodsModule)
      },
      {
        path: 'product-brand',
        loadChildren: () => import('./product-brand/product-brand.module').then(m => m.ResourceProductBrandModule)
      },
      {
        path: 'product-category',
        loadChildren: () => import('./product-category/product-category.module').then(m => m.ResourceProductCategoryModule)
      },
      {
        path: 'product-catalog',
        loadChildren: () => import('./product-catalog/product-catalog.module').then(m => m.ResourceProductCatalogModule)
      },
      {
        path: 'currency',
        loadChildren: () => import('./currency/currency.module').then(m => m.ResourceCurrencyModule)
      },
      {
        path: 'photos',
        loadChildren: () => import('./photos/photos.module').then(m => m.ResourcePhotosModule)
      },
      {
        path: 'unit-measure',
        loadChildren: () => import('./unit-measure/unit-measure.module').then(m => m.ResourceUnitMeasureModule)
      },
      {
        path: 'vehicle-temperatures',
        loadChildren: () => import('./vehicle-temperatures/vehicle-temperatures.module').then(m => m.ResourceVehicleTemperaturesModule)
      },
      {
        path: 'shopping-carts',
        loadChildren: () => import('./shopping-carts/shopping-carts.module').then(m => m.ResourceShoppingCartsModule)
      },
      {
        path: 'shopping-cart-items',
        loadChildren: () => import('./shopping-cart-items/shopping-cart-items.module').then(m => m.ResourceShoppingCartItemsModule)
      },
      {
        path: 'wishlists',
        loadChildren: () => import('./wishlists/wishlists.module').then(m => m.ResourceWishlistsModule)
      },
      {
        path: 'wishlist-products',
        loadChildren: () => import('./wishlist-products/wishlist-products.module').then(m => m.ResourceWishlistProductsModule)
      },
      {
        path: 'compares',
        loadChildren: () => import('./compares/compares.module').then(m => m.ResourceComparesModule)
      },
      {
        path: 'compare-products',
        loadChildren: () => import('./compare-products/compare-products.module').then(m => m.ResourceCompareProductsModule)
      },
      {
        path: 'reviews',
        loadChildren: () => import('./reviews/reviews.module').then(m => m.ResourceReviewsModule)
      },
      {
        path: 'review-lines',
        loadChildren: () => import('./review-lines/review-lines.module').then(m => m.ResourceReviewLinesModule)
      },
      {
        path: 'product-tags',
        loadChildren: () => import('./product-tags/product-tags.module').then(m => m.ResourceProductTagsModule)
      },
      {
        path: 'supplier-imported-document',
        loadChildren: () =>
          import('./supplier-imported-document/supplier-imported-document.module').then(m => m.ResourceSupplierImportedDocumentModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ResourceEntityModule {}
