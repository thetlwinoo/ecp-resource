import { element, by, ElementFinder } from 'protractor';

export class CultureComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-culture div table .btn-danger'));
  title = element.all(by.css('jhi-culture div h2#page-heading span')).first();

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

export class CultureUpdatePage {
  pageTitle = element(by.id('jhi-culture-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  cultureCodeInput = element(by.id('field_cultureCode'));
  cultureNameInput = element(by.id('field_cultureName'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCultureCodeInput(cultureCode) {
    await this.cultureCodeInput.sendKeys(cultureCode);
  }

  async getCultureCodeInput() {
    return await this.cultureCodeInput.getAttribute('value');
  }

  async setCultureNameInput(cultureName) {
    await this.cultureNameInput.sendKeys(cultureName);
  }

  async getCultureNameInput() {
    return await this.cultureNameInput.getAttribute('value');
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

export class CultureDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-culture-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-culture'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
