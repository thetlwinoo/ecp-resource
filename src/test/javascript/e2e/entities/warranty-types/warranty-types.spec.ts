// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { WarrantyTypesComponentsPage, WarrantyTypesDeleteDialog, WarrantyTypesUpdatePage } from './warranty-types.page-object';

const expect = chai.expect;

describe('WarrantyTypes e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let warrantyTypesComponentsPage: WarrantyTypesComponentsPage;
  let warrantyTypesUpdatePage: WarrantyTypesUpdatePage;
  let warrantyTypesDeleteDialog: WarrantyTypesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WarrantyTypes', async () => {
    await navBarPage.goToEntity('warranty-types');
    warrantyTypesComponentsPage = new WarrantyTypesComponentsPage();
    await browser.wait(ec.visibilityOf(warrantyTypesComponentsPage.title), 5000);
    expect(await warrantyTypesComponentsPage.getTitle()).to.eq('resourceApp.warrantyTypes.home.title');
  });

  it('should load create WarrantyTypes page', async () => {
    await warrantyTypesComponentsPage.clickOnCreateButton();
    warrantyTypesUpdatePage = new WarrantyTypesUpdatePage();
    expect(await warrantyTypesUpdatePage.getPageTitle()).to.eq('resourceApp.warrantyTypes.home.createOrEditLabel');
    await warrantyTypesUpdatePage.cancel();
  });

  it('should create and save WarrantyTypes', async () => {
    const nbButtonsBeforeCreate = await warrantyTypesComponentsPage.countDeleteButtons();

    await warrantyTypesComponentsPage.clickOnCreateButton();
    await promise.all([warrantyTypesUpdatePage.setWarrantyTypeNameInput('warrantyTypeName')]);
    expect(await warrantyTypesUpdatePage.getWarrantyTypeNameInput()).to.eq(
      'warrantyTypeName',
      'Expected WarrantyTypeName value to be equals to warrantyTypeName'
    );
    await warrantyTypesUpdatePage.save();
    expect(await warrantyTypesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await warrantyTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last WarrantyTypes', async () => {
    const nbButtonsBeforeDelete = await warrantyTypesComponentsPage.countDeleteButtons();
    await warrantyTypesComponentsPage.clickOnLastDeleteButton();

    warrantyTypesDeleteDialog = new WarrantyTypesDeleteDialog();
    expect(await warrantyTypesDeleteDialog.getDialogTitle()).to.eq('resourceApp.warrantyTypes.delete.question');
    await warrantyTypesDeleteDialog.clickOnConfirmButton();

    expect(await warrantyTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
