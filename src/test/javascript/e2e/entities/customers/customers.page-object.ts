import { element, by, ElementFinder } from 'protractor';

export class CustomersComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-customers div table .btn-danger'));
  title = element.all(by.css('jhi-customers div h2#page-heading span')).first();

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

export class CustomersUpdatePage {
  pageTitle = element(by.id('jhi-customers-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  accountNumberInput = element(by.id('field_accountNumber'));
  personSelect = element(by.id('field_person'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAccountNumberInput(accountNumber) {
    await this.accountNumberInput.sendKeys(accountNumber);
  }

  async getAccountNumberInput() {
    return await this.accountNumberInput.getAttribute('value');
  }

  async personSelectLastOption(timeout?: number) {
    await this.personSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async personSelectOption(option) {
    await this.personSelect.sendKeys(option);
  }

  getPersonSelect(): ElementFinder {
    return this.personSelect;
  }

  async getPersonSelectedOption() {
    return await this.personSelect.element(by.css('option:checked')).getText();
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

export class CustomersDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-customers-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-customers'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
