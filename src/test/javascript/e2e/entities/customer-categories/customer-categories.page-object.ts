import { element, by, ElementFinder } from 'protractor';

export class CustomerCategoriesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-customer-categories div table .btn-danger'));
  title = element.all(by.css('jhi-customer-categories div h2#page-heading span')).first();

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

export class CustomerCategoriesUpdatePage {
  pageTitle = element(by.id('jhi-customer-categories-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  customerCategoryNameInput = element(by.id('field_customerCategoryName'));
  validFromInput = element(by.id('field_validFrom'));
  validToInput = element(by.id('field_validTo'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCustomerCategoryNameInput(customerCategoryName) {
    await this.customerCategoryNameInput.sendKeys(customerCategoryName);
  }

  async getCustomerCategoryNameInput() {
    return await this.customerCategoryNameInput.getAttribute('value');
  }

  async setValidFromInput(validFrom) {
    await this.validFromInput.sendKeys(validFrom);
  }

  async getValidFromInput() {
    return await this.validFromInput.getAttribute('value');
  }

  async setValidToInput(validTo) {
    await this.validToInput.sendKeys(validTo);
  }

  async getValidToInput() {
    return await this.validToInput.getAttribute('value');
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

export class CustomerCategoriesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-customerCategories-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-customerCategories'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
