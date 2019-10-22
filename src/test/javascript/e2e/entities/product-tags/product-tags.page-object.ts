import { element, by, ElementFinder } from 'protractor';

export class ProductTagsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-tags div table .btn-danger'));
  title = element.all(by.css('jhi-product-tags div h2#page-heading span')).first();

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

export class ProductTagsUpdatePage {
  pageTitle = element(by.id('jhi-product-tags-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  tagNameInput = element(by.id('field_tagName'));
  productSelect = element(by.id('field_product'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTagNameInput(tagName) {
    await this.tagNameInput.sendKeys(tagName);
  }

  async getTagNameInput() {
    return await this.tagNameInput.getAttribute('value');
  }

  async productSelectLastOption(timeout?: number) {
    await this.productSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async productSelectOption(option) {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption() {
    return await this.productSelect.element(by.css('option:checked')).getText();
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

export class ProductTagsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productTags-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productTags'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
