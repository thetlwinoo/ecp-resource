// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { StateProvincesComponentsPage, StateProvincesDeleteDialog, StateProvincesUpdatePage } from './state-provinces.page-object';

const expect = chai.expect;

describe('StateProvinces e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let stateProvincesComponentsPage: StateProvincesComponentsPage;
  let stateProvincesUpdatePage: StateProvincesUpdatePage;
  let stateProvincesDeleteDialog: StateProvincesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StateProvinces', async () => {
    await navBarPage.goToEntity('state-provinces');
    stateProvincesComponentsPage = new StateProvincesComponentsPage();
    await browser.wait(ec.visibilityOf(stateProvincesComponentsPage.title), 5000);
    expect(await stateProvincesComponentsPage.getTitle()).to.eq('resourceApp.stateProvinces.home.title');
  });

  it('should load create StateProvinces page', async () => {
    await stateProvincesComponentsPage.clickOnCreateButton();
    stateProvincesUpdatePage = new StateProvincesUpdatePage();
    expect(await stateProvincesUpdatePage.getPageTitle()).to.eq('resourceApp.stateProvinces.home.createOrEditLabel');
    await stateProvincesUpdatePage.cancel();
  });

  it('should create and save StateProvinces', async () => {
    const nbButtonsBeforeCreate = await stateProvincesComponentsPage.countDeleteButtons();

    await stateProvincesComponentsPage.clickOnCreateButton();
    await promise.all([
      stateProvincesUpdatePage.setStateProvinceCodeInput('stateProvinceCode'),
      stateProvincesUpdatePage.setStateProvinceNameInput('stateProvinceName'),
      stateProvincesUpdatePage.setSalesTerritoryInput('salesTerritory'),
      stateProvincesUpdatePage.setBorderInput('border'),
      stateProvincesUpdatePage.setLatestRecordedPopulationInput('5'),
      stateProvincesUpdatePage.setValidFromInput('2000-12-31'),
      stateProvincesUpdatePage.setValidToInput('2000-12-31'),
      stateProvincesUpdatePage.countrySelectLastOption()
    ]);
    expect(await stateProvincesUpdatePage.getStateProvinceCodeInput()).to.eq(
      'stateProvinceCode',
      'Expected StateProvinceCode value to be equals to stateProvinceCode'
    );
    expect(await stateProvincesUpdatePage.getStateProvinceNameInput()).to.eq(
      'stateProvinceName',
      'Expected StateProvinceName value to be equals to stateProvinceName'
    );
    expect(await stateProvincesUpdatePage.getSalesTerritoryInput()).to.eq(
      'salesTerritory',
      'Expected SalesTerritory value to be equals to salesTerritory'
    );
    expect(await stateProvincesUpdatePage.getBorderInput()).to.eq('border', 'Expected Border value to be equals to border');
    expect(await stateProvincesUpdatePage.getLatestRecordedPopulationInput()).to.eq(
      '5',
      'Expected latestRecordedPopulation value to be equals to 5'
    );
    expect(await stateProvincesUpdatePage.getValidFromInput()).to.eq('2000-12-31', 'Expected validFrom value to be equals to 2000-12-31');
    expect(await stateProvincesUpdatePage.getValidToInput()).to.eq('2000-12-31', 'Expected validTo value to be equals to 2000-12-31');
    await stateProvincesUpdatePage.save();
    expect(await stateProvincesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await stateProvincesComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last StateProvinces', async () => {
    const nbButtonsBeforeDelete = await stateProvincesComponentsPage.countDeleteButtons();
    await stateProvincesComponentsPage.clickOnLastDeleteButton();

    stateProvincesDeleteDialog = new StateProvincesDeleteDialog();
    expect(await stateProvincesDeleteDialog.getDialogTitle()).to.eq('resourceApp.stateProvinces.delete.question');
    await stateProvincesDeleteDialog.clickOnConfirmButton();

    expect(await stateProvincesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
