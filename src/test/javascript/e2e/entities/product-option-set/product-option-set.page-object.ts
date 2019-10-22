import { element, by, ElementFinder } from 'protractor';

export class ProductOptionSetComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-option-set div table .btn-danger'));
  title = element.all(by.css('jhi-product-option-set div h2#page-heading span')).first();

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

export class ProductOptionSetUpdatePage {
  pageTitle = element(by.id('jhi-product-option-set-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  productOptionSetValueInput = element(by.id('field_productOptionSetValue'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setProductOptionSetValueInput(productOptionSetValue) {
    await this.productOptionSetValueInput.sendKeys(productOptionSetValue);
  }

  async getProductOptionSetValueInput() {
    return await this.productOptionSetValueInput.getAttribute('value');
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

export class ProductOptionSetDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productOptionSet-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productOptionSet'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
