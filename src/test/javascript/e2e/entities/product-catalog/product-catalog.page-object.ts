import { element, by, ElementFinder } from 'protractor';

export class ProductCatalogComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-catalog div table .btn-danger'));
  title = element.all(by.css('jhi-product-catalog div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ProductCatalogUpdatePage {
  pageTitle = element(by.id('jhi-product-catalog-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  productCategorySelect = element(by.id('field_productCategory'));
  productSelect = element(by.id('field_product'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async productCategorySelectLastOption(timeout?: number) {
    await this.productCategorySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productCategorySelectOption(option) {
    await this.productCategorySelect.sendKeys(option);
  }

  getProductCategorySelect(): ElementFinder {
    return this.productCategorySelect;
  }

  async getProductCategorySelectedOption() {
    return await this.productCategorySelect.element(by.css('option:checked')).getText();
  }

  async productSelectLastOption(timeout?: number) {
    await this.productSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productSelectOption(option) {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption() {
    return await this.productSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ProductCatalogDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productCatalog-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productCatalog'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
