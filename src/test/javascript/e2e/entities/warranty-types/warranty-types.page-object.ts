import { element, by, ElementFinder } from 'protractor';

export class WarrantyTypesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-warranty-types div table .btn-danger'));
  title = element.all(by.css('jhi-warranty-types div h2#page-heading span')).first();

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

export class WarrantyTypesUpdatePage {
  pageTitle = element(by.id('jhi-warranty-types-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  warrantyTypeNameInput = element(by.id('field_warrantyTypeName'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setWarrantyTypeNameInput(warrantyTypeName) {
    await this.warrantyTypeNameInput.sendKeys(warrantyTypeName);
  }

  async getWarrantyTypeNameInput() {
    return await this.warrantyTypeNameInput.getAttribute('value');
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

export class WarrantyTypesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-warrantyTypes-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-warrantyTypes'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
