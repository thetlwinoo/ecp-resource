import { element, by, ElementFinder } from 'protractor';

export class DeliveryMethodsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-delivery-methods div table .btn-danger'));
  title = element.all(by.css('jhi-delivery-methods div h2#page-heading span')).first();

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

export class DeliveryMethodsUpdatePage {
  pageTitle = element(by.id('jhi-delivery-methods-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  deliveryMethodNameInput = element(by.id('field_deliveryMethodName'));
  validFromInput = element(by.id('field_validFrom'));
  validToInput = element(by.id('field_validTo'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDeliveryMethodNameInput(deliveryMethodName) {
    await this.deliveryMethodNameInput.sendKeys(deliveryMethodName);
  }

  async getDeliveryMethodNameInput() {
    return await this.deliveryMethodNameInput.getAttribute('value');
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

export class DeliveryMethodsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-deliveryMethods-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-deliveryMethods'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
