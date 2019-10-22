import { element, by, ElementFinder } from 'protractor';

export class ProductOptionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-option div table .btn-danger'));
  title = element.all(by.css('jhi-product-option div h2#page-heading span')).first();

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

export class ProductOptionUpdatePage {
  pageTitle = element(by.id('jhi-product-option-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  productOptionValueInput = element(by.id('field_productOptionValue'));
  productOptionSetSelect = element(by.id('field_productOptionSet'));
  supplierSelect = element(by.id('field_supplier'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setProductOptionValueInput(productOptionValue) {
    await this.productOptionValueInput.sendKeys(productOptionValue);
  }

  async getProductOptionValueInput() {
    return await this.productOptionValueInput.getAttribute('value');
  }

  async productOptionSetSelectLastOption(timeout?: number) {
    await this.productOptionSetSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productOptionSetSelectOption(option) {
    await this.productOptionSetSelect.sendKeys(option);
  }

  getProductOptionSetSelect(): ElementFinder {
    return this.productOptionSetSelect;
  }

  async getProductOptionSetSelectedOption() {
    return await this.productOptionSetSelect.element(by.css('option:checked')).getText();
  }

  async supplierSelectLastOption(timeout?: number) {
    await this.supplierSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async supplierSelectOption(option) {
    await this.supplierSelect.sendKeys(option);
  }

  getSupplierSelect(): ElementFinder {
    return this.supplierSelect;
  }

  async getSupplierSelectedOption() {
    return await this.supplierSelect.element(by.css('option:checked')).getText();
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

export class ProductOptionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productOption-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productOption'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
