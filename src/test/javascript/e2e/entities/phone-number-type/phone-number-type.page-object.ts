import { element, by, ElementFinder } from 'protractor';

export class PhoneNumberTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-phone-number-type div table .btn-danger'));
  title = element.all(by.css('jhi-phone-number-type div h2#page-heading span')).first();

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

export class PhoneNumberTypeUpdatePage {
  pageTitle = element(by.id('jhi-phone-number-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  phoneNumberTypeNameInput = element(by.id('field_phoneNumberTypeName'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPhoneNumberTypeNameInput(phoneNumberTypeName) {
    await this.phoneNumberTypeNameInput.sendKeys(phoneNumberTypeName);
  }

  async getPhoneNumberTypeNameInput() {
    return await this.phoneNumberTypeNameInput.getAttribute('value');
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

export class PhoneNumberTypeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-phoneNumberType-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-phoneNumberType'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
