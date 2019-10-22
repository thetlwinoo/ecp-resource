import { element, by, ElementFinder } from 'protractor';

export class AddressTypesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-address-types div table .btn-danger'));
  title = element.all(by.css('jhi-address-types div h2#page-heading span')).first();

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

export class AddressTypesUpdatePage {
  pageTitle = element(by.id('jhi-address-types-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  addressTypeNameInput = element(by.id('field_addressTypeName'));
  referInput = element(by.id('field_refer'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAddressTypeNameInput(addressTypeName) {
    await this.addressTypeNameInput.sendKeys(addressTypeName);
  }

  async getAddressTypeNameInput() {
    return await this.addressTypeNameInput.getAttribute('value');
  }

  async setReferInput(refer) {
    await this.referInput.sendKeys(refer);
  }

  async getReferInput() {
    return await this.referInput.getAttribute('value');
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

export class AddressTypesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-addressTypes-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-addressTypes'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
