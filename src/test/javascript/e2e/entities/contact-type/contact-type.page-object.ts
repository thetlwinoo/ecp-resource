import { element, by, ElementFinder } from 'protractor';

export class ContactTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-contact-type div table .btn-danger'));
  title = element.all(by.css('jhi-contact-type div h2#page-heading span')).first();

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

export class ContactTypeUpdatePage {
  pageTitle = element(by.id('jhi-contact-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  contactTypeNameInput = element(by.id('field_contactTypeName'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setContactTypeNameInput(contactTypeName) {
    await this.contactTypeNameInput.sendKeys(contactTypeName);
  }

  async getContactTypeNameInput() {
    return await this.contactTypeNameInput.getAttribute('value');
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

export class ContactTypeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-contactType-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-contactType'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
