import { element, by, ElementFinder } from 'protractor';

export class MaterialsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-materials div table .btn-danger'));
  title = element.all(by.css('jhi-materials div h2#page-heading span')).first();

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

export class MaterialsUpdatePage {
  pageTitle = element(by.id('jhi-materials-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  materialNameInput = element(by.id('field_materialName'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMaterialNameInput(materialName) {
    await this.materialNameInput.sendKeys(materialName);
  }

  async getMaterialNameInput() {
    return await this.materialNameInput.getAttribute('value');
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

export class MaterialsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-materials-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-materials'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
