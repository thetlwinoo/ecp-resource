// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { MaterialsComponentsPage, MaterialsDeleteDialog, MaterialsUpdatePage } from './materials.page-object';

const expect = chai.expect;

describe('Materials e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let materialsComponentsPage: MaterialsComponentsPage;
  let materialsUpdatePage: MaterialsUpdatePage;
  let materialsDeleteDialog: MaterialsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Materials', async () => {
    await navBarPage.goToEntity('materials');
    materialsComponentsPage = new MaterialsComponentsPage();
    await browser.wait(ec.visibilityOf(materialsComponentsPage.title), 5000);
    expect(await materialsComponentsPage.getTitle()).to.eq('resourceApp.materials.home.title');
  });

  it('should load create Materials page', async () => {
    await materialsComponentsPage.clickOnCreateButton();
    materialsUpdatePage = new MaterialsUpdatePage();
    expect(await materialsUpdatePage.getPageTitle()).to.eq('resourceApp.materials.home.createOrEditLabel');
    await materialsUpdatePage.cancel();
  });

  it('should create and save Materials', async () => {
    const nbButtonsBeforeCreate = await materialsComponentsPage.countDeleteButtons();

    await materialsComponentsPage.clickOnCreateButton();
    await promise.all([materialsUpdatePage.setMaterialNameInput('materialName')]);
    expect(await materialsUpdatePage.getMaterialNameInput()).to.eq(
      'materialName',
      'Expected MaterialName value to be equals to materialName'
    );
    await materialsUpdatePage.save();
    expect(await materialsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await materialsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Materials', async () => {
    const nbButtonsBeforeDelete = await materialsComponentsPage.countDeleteButtons();
    await materialsComponentsPage.clickOnLastDeleteButton();

    materialsDeleteDialog = new MaterialsDeleteDialog();
    expect(await materialsDeleteDialog.getDialogTitle()).to.eq('resourceApp.materials.delete.question');
    await materialsDeleteDialog.clickOnConfirmButton();

    expect(await materialsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
