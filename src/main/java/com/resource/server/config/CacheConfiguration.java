package com.resource.server.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.resource.server.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.resource.server.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.resource.server.domain.User.class.getName());
            createCache(cm, com.resource.server.domain.Authority.class.getName());
            createCache(cm, com.resource.server.domain.User.class.getName() + ".authorities");
            createCache(cm, com.resource.server.domain.AddressTypes.class.getName());
            createCache(cm, com.resource.server.domain.Culture.class.getName());
            createCache(cm, com.resource.server.domain.Addresses.class.getName());
            createCache(cm, com.resource.server.domain.BusinessEntityAddress.class.getName());
            createCache(cm, com.resource.server.domain.ShipMethod.class.getName());
            createCache(cm, com.resource.server.domain.PersonEmailAddress.class.getName());
            createCache(cm, com.resource.server.domain.PersonPhone.class.getName());
            createCache(cm, com.resource.server.domain.PhoneNumberType.class.getName());
            createCache(cm, com.resource.server.domain.ContactType.class.getName());
            createCache(cm, com.resource.server.domain.BusinessEntityContact.class.getName());
            createCache(cm, com.resource.server.domain.Countries.class.getName());
            createCache(cm, com.resource.server.domain.StateProvinces.class.getName());
            createCache(cm, com.resource.server.domain.Cities.class.getName());
            createCache(cm, com.resource.server.domain.SystemParameters.class.getName());
            createCache(cm, com.resource.server.domain.TransactionTypes.class.getName());
            createCache(cm, com.resource.server.domain.People.class.getName());
            createCache(cm, com.resource.server.domain.PaymentMethods.class.getName());
            createCache(cm, com.resource.server.domain.DeliveryMethods.class.getName());
            createCache(cm, com.resource.server.domain.SupplierCategories.class.getName());
            createCache(cm, com.resource.server.domain.Suppliers.class.getName());
            createCache(cm, com.resource.server.domain.SupplierTransactions.class.getName());
            createCache(cm, com.resource.server.domain.CurrencyRate.class.getName());
            createCache(cm, com.resource.server.domain.PurchaseOrders.class.getName());
            createCache(cm, com.resource.server.domain.PurchaseOrders.class.getName() + ".purchaseOrderLineLists");
            createCache(cm, com.resource.server.domain.PurchaseOrderLines.class.getName());
            createCache(cm, com.resource.server.domain.BuyingGroups.class.getName());
            createCache(cm, com.resource.server.domain.CustomerCategories.class.getName());
            createCache(cm, com.resource.server.domain.Customers.class.getName());
            createCache(cm, com.resource.server.domain.CustomerTransactions.class.getName());
            createCache(cm, com.resource.server.domain.PaymentTransactions.class.getName());
            createCache(cm, com.resource.server.domain.InvoiceLines.class.getName());
            createCache(cm, com.resource.server.domain.Invoices.class.getName());
            createCache(cm, com.resource.server.domain.Invoices.class.getName() + ".invoiceLineLists");
            createCache(cm, com.resource.server.domain.OrderLines.class.getName());
            createCache(cm, com.resource.server.domain.Orders.class.getName());
            createCache(cm, com.resource.server.domain.Orders.class.getName() + ".orderLineLists");
            createCache(cm, com.resource.server.domain.SpecialDeals.class.getName());
            createCache(cm, com.resource.server.domain.SpecialDeals.class.getName() + ".cartDiscounts");
            createCache(cm, com.resource.server.domain.SpecialDeals.class.getName() + ".orderDiscounts");
            createCache(cm, com.resource.server.domain.ColdRoomTemperatures.class.getName());
            createCache(cm, com.resource.server.domain.PackageTypes.class.getName());
            createCache(cm, com.resource.server.domain.Products.class.getName());
            createCache(cm, com.resource.server.domain.Products.class.getName() + ".stockItemLists");
            createCache(cm, com.resource.server.domain.BarcodeTypes.class.getName());
            createCache(cm, com.resource.server.domain.StockItems.class.getName());
            createCache(cm, com.resource.server.domain.StockItems.class.getName() + ".photoLists");
            createCache(cm, com.resource.server.domain.StockItems.class.getName() + ".dangerousGoodLists");
            createCache(cm, com.resource.server.domain.StockItems.class.getName() + ".specialDiscounts");
            createCache(cm, com.resource.server.domain.StockItemTemp.class.getName());
            createCache(cm, com.resource.server.domain.UploadTransactions.class.getName());
            createCache(cm, com.resource.server.domain.UploadTransactions.class.getName() + ".importDocumentLists");
            createCache(cm, com.resource.server.domain.UploadTransactions.class.getName() + ".stockItemTempLists");
            createCache(cm, com.resource.server.domain.UploadActionTypes.class.getName());
            createCache(cm, com.resource.server.domain.StockItemTransactions.class.getName());
            createCache(cm, com.resource.server.domain.StockItemHoldings.class.getName());
            createCache(cm, com.resource.server.domain.WarrantyTypes.class.getName());
            createCache(cm, com.resource.server.domain.ProductAttribute.class.getName());
            createCache(cm, com.resource.server.domain.ProductAttributeSet.class.getName());
            createCache(cm, com.resource.server.domain.ProductOption.class.getName());
            createCache(cm, com.resource.server.domain.ProductOptionSet.class.getName());
            createCache(cm, com.resource.server.domain.ProductChoice.class.getName());
            createCache(cm, com.resource.server.domain.ProductSet.class.getName());
            createCache(cm, com.resource.server.domain.ProductSetDetails.class.getName());
            createCache(cm, com.resource.server.domain.ProductDocument.class.getName());
            createCache(cm, com.resource.server.domain.Materials.class.getName());
            createCache(cm, com.resource.server.domain.DangerousGoods.class.getName());
            createCache(cm, com.resource.server.domain.ProductBrand.class.getName());
            createCache(cm, com.resource.server.domain.ProductCategory.class.getName());
            createCache(cm, com.resource.server.domain.ProductCatalog.class.getName());
            createCache(cm, com.resource.server.domain.Currency.class.getName());
            createCache(cm, com.resource.server.domain.Photos.class.getName());
            createCache(cm, com.resource.server.domain.UnitMeasure.class.getName());
            createCache(cm, com.resource.server.domain.VehicleTemperatures.class.getName());
            createCache(cm, com.resource.server.domain.ShoppingCarts.class.getName());
            createCache(cm, com.resource.server.domain.ShoppingCarts.class.getName() + ".cartItemLists");
            createCache(cm, com.resource.server.domain.ShoppingCartItems.class.getName());
            createCache(cm, com.resource.server.domain.Wishlists.class.getName());
            createCache(cm, com.resource.server.domain.Wishlists.class.getName() + ".wishlistLists");
            createCache(cm, com.resource.server.domain.WishlistProducts.class.getName());
            createCache(cm, com.resource.server.domain.Compares.class.getName());
            createCache(cm, com.resource.server.domain.Compares.class.getName() + ".compareLists");
            createCache(cm, com.resource.server.domain.CompareProducts.class.getName());
            createCache(cm, com.resource.server.domain.Reviews.class.getName());
            createCache(cm, com.resource.server.domain.Reviews.class.getName() + ".reviewLineLists");
            createCache(cm, com.resource.server.domain.ReviewLines.class.getName());
            createCache(cm, com.resource.server.domain.ProductTags.class.getName());
            createCache(cm, com.resource.server.domain.SupplierImportedDocument.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
