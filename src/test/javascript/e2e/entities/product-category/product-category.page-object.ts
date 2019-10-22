import { element, by, ElementFinder } from 'protractor';

export class ProductCategoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-product-category div table .btn-danger'));
  title = element.all(by.css('jhi-product-category div h2#page-heading span')).first();

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

export class ProductCategoryUpdatePage {
  pageTitle = element(by.id('jhi-product-category-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nameInput = element(by.id('field_name'));
  labelInput = element(by.id('field_label'));
  photoInput = element(by.id('file_photo'));
  parentSelect = element(by.id('field_parent'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return await this.nameInput.getAttribute('value');
  }

  async setLabelInput(label) {
    await this.labelInput.sendKeys(label);
  }

  async getLabelInput() {
    return await this.labelInput.getAttribute('value');
  }

  async setPhotoInput(photo) {
    await this.photoInput.sendKeys(photo);
  }

  async getPhotoInput() {
    return await this.photoInput.getAttribute('value');
  }

  async parentSelectLastOption(timeout?: number) {
    await this.parentSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async parentSelectOption(option) {
    await this.parentSelect.sendKeys(option);
  }

  getParentSelect(): ElementFinder {
    return this.parentSelect;
  }

  async getParentSelectedOption() {
    return await this.parentSelect.element(by.css('option:checked')).getText();
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

export class ProductCategoryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-productCategory-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-productCategory'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
