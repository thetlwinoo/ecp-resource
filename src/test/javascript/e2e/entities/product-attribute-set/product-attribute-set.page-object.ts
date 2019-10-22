import { element, by, ElementFinder } from 'protractor';

export class ProductAttributeSetComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-attribute-set div table .btn-danger'));
  title = element.all(by.css('jhi-product-attribute-set div h2#page-heading span')).first();

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

export class ProductAttributeSetUpdatePage {
  pageTitle = element(by.id('jhi-product-attribute-set-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  productAttributeSetNameInput = element(by.id('field_productAttributeSetName'));
  productOptionSetSelect = element(by.id('field_productOptionSet'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setProductAttributeSetNameInput(productAttributeSetName) {
    await this.productAttributeSetNameInput.sendKeys(productAttributeSetName);
  }

  async getProductAttributeSetNameInput() {
    return await this.productAttributeSetNameInput.getAttribute('value');
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

export class ProductAttributeSetDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productAttributeSet-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productAttributeSet'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
