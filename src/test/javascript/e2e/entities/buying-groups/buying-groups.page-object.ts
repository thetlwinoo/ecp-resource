import { element, by, ElementFinder } from 'protractor';

export class BuyingGroupsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-buying-groups div table .btn-danger'));
  title = element.all(by.css('jhi-buying-groups div h2#page-heading span')).first();

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

export class BuyingGroupsUpdatePage {
  pageTitle = element(by.id('jhi-buying-groups-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  buyingGroupNameInput = element(by.id('field_buyingGroupName'));
  validFromInput = element(by.id('field_validFrom'));
  validToInput = element(by.id('field_validTo'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setBuyingGroupNameInput(buyingGroupName) {
    await this.buyingGroupNameInput.sendKeys(buyingGroupName);
  }

  async getBuyingGroupNameInput() {
    return await this.buyingGroupNameInput.getAttribute('value');
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

export class BuyingGroupsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-buyingGroups-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-buyingGroups'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
