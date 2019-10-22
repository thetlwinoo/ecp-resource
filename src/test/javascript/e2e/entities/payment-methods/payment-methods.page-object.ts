import { element, by, ElementFinder } from 'protractor';

export class PaymentMethodsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payment-methods div table .btn-danger'));
  title = element.all(by.css('jhi-payment-methods div h2#page-heading span')).first();

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

export class PaymentMethodsUpdatePage {
  pageTitle = element(by.id('jhi-payment-methods-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  paymentMethodNameInput = element(by.id('field_paymentMethodName'));
  activeIndInput = element(by.id('field_activeInd'));
  validFromInput = element(by.id('field_validFrom'));
  validToInput = element(by.id('field_validTo'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPaymentMethodNameInput(paymentMethodName) {
    await this.paymentMethodNameInput.sendKeys(paymentMethodName);
  }

  async getPaymentMethodNameInput() {
    return await this.paymentMethodNameInput.getAttribute('value');
  }

  getActiveIndInput(timeout?: number) {
    return this.activeIndInput;
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

export class PaymentMethodsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-paymentMethods-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-paymentMethods'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
