// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { TransactionTypesComponentsPage, TransactionTypesDeleteDialog, TransactionTypesUpdatePage } from './transaction-types.page-object';

const expect = chai.expect;

describe('TransactionTypes e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let transactionTypesComponentsPage: TransactionTypesComponentsPage;
  let transactionTypesUpdatePage: TransactionTypesUpdatePage;
  let transactionTypesDeleteDialog: TransactionTypesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TransactionTypes', async () => {
    await navBarPage.goToEntity('transaction-types');
    transactionTypesComponentsPage = new TransactionTypesComponentsPage();
    await browser.wait(ec.visibilityOf(transactionTypesComponentsPage.title), 5000);
    expect(await transactionTypesComponentsPage.getTitle()).to.eq('resourceApp.transactionTypes.home.title');
  });

  it('should load create TransactionTypes page', async () => {
    await transactionTypesComponentsPage.clickOnCreateButton();
    transactionTypesUpdatePage = new TransactionTypesUpdatePage();
    expect(await transactionTypesUpdatePage.getPageTitle()).to.eq('resourceApp.transactionTypes.home.createOrEditLabel');
    await transactionTypesUpdatePage.cancel();
  });

  it('should create and save TransactionTypes', async () => {
    const nbButtonsBeforeCreate = await transactionTypesComponentsPage.countDeleteButtons();

    await transactionTypesComponentsPage.clickOnCreateButton();
    await promise.all([
      transactionTypesUpdatePage.setTransactionTypeNameInput('transactionTypeName'),
      transactionTypesUpdatePage.setValidFromInput('2000-12-31'),
      transactionTypesUpdatePage.setValidToInput('2000-12-31')
    ]);
    expect(await transactionTypesUpdatePage.getTransactionTypeNameInput()).to.eq(
      'transactionTypeName',
      'Expected TransactionTypeName value to be equals to transactionTypeName'
    );
    expect(await transactionTypesUpdatePage.getValidFromInput()).to.eq('2000-12-31', 'Expected validFrom value to be equals to 2000-12-31');
    expect(await transactionTypesUpdatePage.getValidToInput()).to.eq('2000-12-31', 'Expected validTo value to be equals to 2000-12-31');
    await transactionTypesUpdatePage.save();
    expect(await transactionTypesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await transactionTypesComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TransactionTypes', async () => {
    const nbButtonsBeforeDelete = await transactionTypesComponentsPage.countDeleteButtons();
    await transactionTypesComponentsPage.clickOnLastDeleteButton();

    transactionTypesDeleteDialog = new TransactionTypesDeleteDialog();
    expect(await transactionTypesDeleteDialog.getDialogTitle()).to.eq('resourceApp.transactionTypes.delete.question');
    await transactionTypesDeleteDialog.clickOnConfirmButton();

    expect(await transactionTypesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
