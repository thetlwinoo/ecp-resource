import { element, by, ElementFinder } from 'protractor';

export class ProductAttributeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-attribute div table .btn-danger'));
  title = element.all(by.css('jhi-product-attribute div h2#page-heading span')).first();

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

export class ProductAttributeUpdatePage {
  pageTitle = element(by.id('jhi-product-attribute-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  productAttributeValueInput = element(by.id('field_productAttributeValue'));
  productAttributeSetSelect = element(by.id('field_productAttributeSet'));
  supplierSelect = element(by.id('field_supplier'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setProductAttributeValueInput(productAttributeValue) {
    await this.productAttributeValueInput.sendKeys(productAttributeValue);
  }

  async getProductAttributeValueInput() {
    return await this.productAttributeValueInput.getAttribute('value');
  }

  async productAttributeSetSelectLastOption(timeout?: number) {
    await this.productAttributeSetSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productAttributeSetSelectOption(option) {
    await this.productAttributeSetSelect.sendKeys(option);
  }

  getProductAttributeSetSelect(): ElementFinder {
    return this.productAttributeSetSelect;
  }

  async getProductAttributeSetSelectedOption() {
    return await this.productAttributeSetSelect.element(by.css('option:checked')).getText();
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

export class ProductAttributeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productAttribute-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productAttribute'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
