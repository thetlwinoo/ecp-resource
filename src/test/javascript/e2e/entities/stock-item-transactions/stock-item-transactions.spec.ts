// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  StockItemTransactionsComponentsPage,
  StockItemTransactionsDeleteDialog,
  StockItemTransactionsUpdatePage
} from './stock-item-transactions.page-object';

const expect = chai.expect;

describe('StockItemTransactions e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let stockItemTransactionsComponentsPage: StockItemTransactionsComponentsPage;
  let stockItemTransactionsUpdatePage: StockItemTransactionsUpdatePage;
  let stockItemTransactionsDeleteDialog: StockItemTransactionsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StockItemTransactions', async () => {
    await navBarPage.goToEntity('stock-item-transactions');
    stockItemTransactionsComponentsPage = new StockItemTransactionsComponentsPage();
    await browser.wait(ec.visibilityOf(stockItemTransactionsComponentsPage.title), 5000);
    expect(await stockItemTransactionsComponentsPage.getTitle()).to.eq('resourceApp.stockItemTransactions.home.title');
  });

  it('should load create StockItemTransactions page', async () => {
    await stockItemTransactionsComponentsPage.clickOnCreateButton();
    stockItemTransactionsUpdatePage = new StockItemTransactionsUpdatePage();
    expect(await stockItemTransactionsUpdatePage.getPageTitle()).to.eq('resourceApp.stockItemTransactions.home.createOrEditLabel');
    await stockItemTransactionsUpdatePage.cancel();
  });

  it('should create and save StockItemTransactions', async () => {
    const nbButtonsBeforeCreate = await stockItemTransactionsComponentsPage.countDeleteButtons();

    await stockItemTransactionsComponentsPage.clickOnCreateButton();
    await promise.all([
      stockItemTransactionsUpdatePage.setTransactionOccurredWhenInput('2000-12-31'),
      stockItemTransactionsUpdatePage.setQuantityInput('5'),
      stockItemTransactionsUpdatePage.setLastEditedByInput('lastEditedBy'),
      stockItemTransactionsUpdatePage.setLastEditedWhenInput('2000-12-31'),
      stockItemTransactionsUpdatePage.stockItemSelectLastOption(),
      stockItemTransactionsUpdatePage.customerSelectLastOption(),
      stockItemTransactionsUpdatePage.invoiceSelectLastOption(),
      stockItemTransactionsUpdatePage.supplierSelectLastOption(),
      stockItemTransactionsUpdatePage.transactionTypeSelectLastOption(),
      stockItemTransactionsUpdatePage.purchaseOrderSelectLastOption()
    ]);
    expect(await stockItemTransactionsUpdatePage.getTransactionOccurredWhenInput()).to.eq(
      '2000-12-31',
      'Expected transactionOccurredWhen value to be equals to 2000-12-31'
    );
    expect(await stockItemTransactionsUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await stockItemTransactionsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await stockItemTransactionsUpdatePage.getLastEditedWhenInput()).to.eq(
      '2000-12-31',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await stockItemTransactionsUpdatePage.save();
    expect(await stockItemTransactionsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await stockItemTransactionsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last StockItemTransactions', async () => {
    const nbButtonsBeforeDelete = await stockItemTransactionsComponentsPage.countDeleteButtons();
    await stockItemTransactionsComponentsPage.clickOnLastDeleteButton();

    stockItemTransactionsDeleteDialog = new StockItemTransactionsDeleteDialog();
    expect(await stockItemTransactionsDeleteDialog.getDialogTitle()).to.eq('resourceApp.stockItemTransactions.delete.question');
    await stockItemTransactionsDeleteDialog.clickOnConfirmButton();

    expect(await stockItemTransactionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
