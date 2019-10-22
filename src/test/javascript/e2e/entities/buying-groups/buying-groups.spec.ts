// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { BuyingGroupsComponentsPage, BuyingGroupsDeleteDialog, BuyingGroupsUpdatePage } from './buying-groups.page-object';

const expect = chai.expect;

describe('BuyingGroups e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let buyingGroupsComponentsPage: BuyingGroupsComponentsPage;
  let buyingGroupsUpdatePage: BuyingGroupsUpdatePage;
  let buyingGroupsDeleteDialog: BuyingGroupsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BuyingGroups', async () => {
    await navBarPage.goToEntity('buying-groups');
    buyingGroupsComponentsPage = new BuyingGroupsComponentsPage();
    await browser.wait(ec.visibilityOf(buyingGroupsComponentsPage.title), 5000);
    expect(await buyingGroupsComponentsPage.getTitle()).to.eq('resourceApp.buyingGroups.home.title');
  });

  it('should load create BuyingGroups page', async () => {
    await buyingGroupsComponentsPage.clickOnCreateButton();
    buyingGroupsUpdatePage = new BuyingGroupsUpdatePage();
    expect(await buyingGroupsUpdatePage.getPageTitle()).to.eq('resourceApp.buyingGroups.home.createOrEditLabel');
    await buyingGroupsUpdatePage.cancel();
  });

  it('should create and save BuyingGroups', async () => {
    const nbButtonsBeforeCreate = await buyingGroupsComponentsPage.countDeleteButtons();

    await buyingGroupsComponentsPage.clickOnCreateButton();
    await promise.all([
      buyingGroupsUpdatePage.setBuyingGroupNameInput('buyingGroupName'),
      buyingGroupsUpdatePage.setValidFromInput('2000-12-31'),
      buyingGroupsUpdatePage.setValidToInput('2000-12-31')
    ]);
    expect(await buyingGroupsUpdatePage.getBuyingGroupNameInput()).to.eq(
      'buyingGroupName',
      'Expected BuyingGroupName value to be equals to buyingGroupName'
    );
    expect(await buyingGroupsUpdatePage.getValidFromInput()).to.eq('2000-12-31', 'Expected validFrom value to be equals to 2000-12-31');
    expect(await buyingGroupsUpdatePage.getValidToInput()).to.eq('2000-12-31', 'Expected validTo value to be equals to 2000-12-31');
    await buyingGroupsUpdatePage.save();
    expect(await buyingGroupsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await buyingGroupsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last BuyingGroups', async () => {
    const nbButtonsBeforeDelete = await buyingGroupsComponentsPage.countDeleteButtons();
    await buyingGroupsComponentsPage.clickOnLastDeleteButton();

    buyingGroupsDeleteDialog = new BuyingGroupsDeleteDialog();
    expect(await buyingGroupsDeleteDialog.getDialogTitle()).to.eq('resourceApp.buyingGroups.delete.question');
    await buyingGroupsDeleteDialog.clickOnConfirmButton();

    expect(await buyingGroupsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
