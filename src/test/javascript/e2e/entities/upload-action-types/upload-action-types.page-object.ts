import { element, by, ElementFinder } from 'protractor';

export class UploadActionTypesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-upload-action-types div table .btn-danger'));
  title = element.all(by.css('jhi-upload-action-types div h2#page-heading span')).first();

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

export class UploadActionTypesUpdatePage {
  pageTitle = element(by.id('jhi-upload-action-types-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  actionTypeNameInput = element(by.id('field_actionTypeName'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setActionTypeNameInput(actionTypeName) {
    await this.actionTypeNameInput.sendKeys(actionTypeName);
  }

  async getActionTypeNameInput() {
    return await this.actionTypeNameInput.getAttribute('value');
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

export class UploadActionTypesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-uploadActionTypes-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-uploadActionTypes'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
