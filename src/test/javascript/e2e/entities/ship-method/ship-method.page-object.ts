import { element, by, ElementFinder } from 'protractor';

export class ShipMethodComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ship-method div table .btn-danger'));
  title = element.all(by.css('jhi-ship-method div h2#page-heading span')).first();

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

export class ShipMethodUpdatePage {
  pageTitle = element(by.id('jhi-ship-method-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  shipMethodNameInput = element(by.id('field_shipMethodName'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setShipMethodNameInput(shipMethodName) {
    await this.shipMethodNameInput.sendKeys(shipMethodName);
  }

  async getShipMethodNameInput() {
    return await this.shipMethodNameInput.getAttribute('value');
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

export class ShipMethodDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-shipMethod-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-shipMethod'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
