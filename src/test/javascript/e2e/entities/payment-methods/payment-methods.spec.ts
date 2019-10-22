// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { PaymentMethodsComponentsPage, PaymentMethodsDeleteDialog, PaymentMethodsUpdatePage } from './payment-methods.page-object';

const expect = chai.expect;

describe('PaymentMethods e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentMethodsComponentsPage: PaymentMethodsComponentsPage;
  let paymentMethodsUpdatePage: PaymentMethodsUpdatePage;
  let paymentMethodsDeleteDialog: PaymentMethodsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentMethods', async () => {
    await navBarPage.goToEntity('payment-methods');
    paymentMethodsComponentsPage = new PaymentMethodsComponentsPage();
    await browser.wait(ec.visibilityOf(paymentMethodsComponentsPage.title), 5000);
    expect(await paymentMethodsComponentsPage.getTitle()).to.eq('resourceApp.paymentMethods.home.title');
  });

  it('should load create PaymentMethods page', async () => {
    await paymentMethodsComponentsPage.clickOnCreateButton();
    paymentMethodsUpdatePage = new PaymentMethodsUpdatePage();
    expect(await paymentMethodsUpdatePage.getPageTitle()).to.eq('resourceApp.paymentMethods.home.createOrEditLabel');
    await paymentMethodsUpdatePage.cancel();
  });

  it('should create and save PaymentMethods', async () => {
    const nbButtonsBeforeCreate = await paymentMethodsComponentsPage.countDeleteButtons();

    await paymentMethodsComponentsPage.clickOnCreateButton();
    await promise.all([
      paymentMethodsUpdatePage.setPaymentMethodNameInput('paymentMethodName'),
      paymentMethodsUpdatePage.setValidFromInput('2000-12-31'),
      paymentMethodsUpdatePage.setValidToInput('2000-12-31')
    ]);
    expect(await paymentMethodsUpdatePage.getPaymentMethodNameInput()).to.eq(
      'paymentMethodName',
      'Expected PaymentMethodName value to be equals to paymentMethodName'
    );
    const selectedActiveInd = paymentMethodsUpdatePage.getActiveIndInput();
    if (await selectedActiveInd.isSelected()) {
      await paymentMethodsUpdatePage.getActiveIndInput().click();
      expect(await paymentMethodsUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd not to be selected').to.be.false;
    } else {
      await paymentMethodsUpdatePage.getActiveIndInput().click();
      expect(await paymentMethodsUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd to be selected').to.be.true;
    }
    expect(await paymentMethodsUpdatePage.getValidFromInput()).to.eq('2000-12-31', 'Expected validFrom value to be equals to 2000-12-31');
    expect(await paymentMethodsUpdatePage.getValidToInput()).to.eq('2000-12-31', 'Expected validTo value to be equals to 2000-12-31');
    await paymentMethodsUpdatePage.save();
    expect(await paymentMethodsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentMethodsComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentMethods', async () => {
    const nbButtonsBeforeDelete = await paymentMethodsComponentsPage.countDeleteButtons();
    await paymentMethodsComponentsPage.clickOnLastDeleteButton();

    paymentMethodsDeleteDialog = new PaymentMethodsDeleteDialog();
    expect(await paymentMethodsDeleteDialog.getDialogTitle()).to.eq('resourceApp.paymentMethods.delete.question');
    await paymentMethodsDeleteDialog.clickOnConfirmButton();

    expect(await paymentMethodsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
