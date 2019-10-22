import { element, by, ElementFinder } from 'protractor';

export class ComparesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-compares div table .btn-danger'));
  title = element.all(by.css('jhi-compares div h2#page-heading span')).first();

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

export class ComparesUpdatePage {
  pageTitle = element(by.id('jhi-compares-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  compareUserSelect = element(by.id('field_compareUser'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async compareUserSelectLastOption(timeout?: number) {
    await this.compareUserSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async compareUserSelectOption(option) {
    await this.compareUserSelect.sendKeys(option);
  }

  getCompareUserSelect(): ElementFinder {
    return this.compareUserSelect;
  }

  async getCompareUserSelectedOption() {
    return await this.compareUserSelect.element(by.css('option:checked')).getText();
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

export class ComparesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-compares-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-compares'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
