// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { UnitMeasureComponentsPage, UnitMeasureDeleteDialog, UnitMeasureUpdatePage } from './unit-measure.page-object';

const expect = chai.expect;

describe('UnitMeasure e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let unitMeasureComponentsPage: UnitMeasureComponentsPage;
  let unitMeasureUpdatePage: UnitMeasureUpdatePage;
  let unitMeasureDeleteDialog: UnitMeasureDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UnitMeasures', async () => {
    await navBarPage.goToEntity('unit-measure');
    unitMeasureComponentsPage = new UnitMeasureComponentsPage();
    await browser.wait(ec.visibilityOf(unitMeasureComponentsPage.title), 5000);
    expect(await unitMeasureComponentsPage.getTitle()).to.eq('resourceApp.unitMeasure.home.title');
  });

  it('should load create UnitMeasure page', async () => {
    await unitMeasureComponentsPage.clickOnCreateButton();
    unitMeasureUpdatePage = new UnitMeasureUpdatePage();
    expect(await unitMeasureUpdatePage.getPageTitle()).to.eq('resourceApp.unitMeasure.home.createOrEditLabel');
    await unitMeasureUpdatePage.cancel();
  });

  it('should create and save UnitMeasures', async () => {
    const nbButtonsBeforeCreate = await unitMeasureComponentsPage.countDeleteButtons();

    await unitMeasureComponentsPage.clickOnCreateButton();
    await promise.all([
      unitMeasureUpdatePage.setUnitMeasureCodeInput('unitMeasureCode'),
      unitMeasureUpdatePage.setUnitMeasureNameInput('unitMeasureName')
    ]);
    expect(await unitMeasureUpdatePage.getUnitMeasureCodeInput()).to.eq(
      'unitMeasureCode',
      'Expected UnitMeasureCode value to be equals to unitMeasureCode'
    );
    expect(await unitMeasureUpdatePage.getUnitMeasureNameInput()).to.eq(
      'unitMeasureName',
      'Expected UnitMeasureName value to be equals to unitMeasureName'
    );
    await unitMeasureUpdatePage.save();
    expect(await unitMeasureUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await unitMeasureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last UnitMeasure', async () => {
    const nbButtonsBeforeDelete = await unitMeasureComponentsPage.countDeleteButtons();
    await unitMeasureComponentsPage.clickOnLastDeleteButton();

    unitMeasureDeleteDialog = new UnitMeasureDeleteDialog();
    expect(await unitMeasureDeleteDialog.getDialogTitle()).to.eq('resourceApp.unitMeasure.delete.question');
    await unitMeasureDeleteDialog.clickOnConfirmButton();

    expect(await unitMeasureComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
