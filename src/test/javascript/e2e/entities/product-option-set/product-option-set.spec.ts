// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ProductOptionSetComponentsPage, ProductOptionSetDeleteDialog, ProductOptionSetUpdatePage } from './product-option-set.page-object';

const expect = chai.expect;

describe('ProductOptionSet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productOptionSetComponentsPage: ProductOptionSetComponentsPage;
  let productOptionSetUpdatePage: ProductOptionSetUpdatePage;
  let productOptionSetDeleteDialog: ProductOptionSetDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductOptionSets', async () => {
    await navBarPage.goToEntity('product-option-set');
    productOptionSetComponentsPage = new ProductOptionSetComponentsPage();
    await browser.wait(ec.visibilityOf(productOptionSetComponentsPage.title), 5000);
    expect(await productOptionSetComponentsPage.getTitle()).to.eq('resourceApp.productOptionSet.home.title');
  });

  it('should load create ProductOptionSet page', async () => {
    await productOptionSetComponentsPage.clickOnCreateButton();
    productOptionSetUpdatePage = new ProductOptionSetUpdatePage();
    expect(await productOptionSetUpdatePage.getPageTitle()).to.eq('resourceApp.productOptionSet.home.createOrEditLabel');
    await productOptionSetUpdatePage.cancel();
  });

  it('should create and save ProductOptionSets', async () => {
    const nbButtonsBeforeCreate = await productOptionSetComponentsPage.countDeleteButtons();

    await productOptionSetComponentsPage.clickOnCreateButton();
    await promise.all([productOptionSetUpdatePage.setProductOptionSetValueInput('productOptionSetValue')]);
    expect(await productOptionSetUpdatePage.getProductOptionSetValueInput()).to.eq(
      'productOptionSetValue',
      'Expected ProductOptionSetValue value to be equals to productOptionSetValue'
    );
    await productOptionSetUpdatePage.save();
    expect(await productOptionSetUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productOptionSetComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductOptionSet', async () => {
    const nbButtonsBeforeDelete = await productOptionSetComponentsPage.countDeleteButtons();
    await productOptionSetComponentsPage.clickOnLastDeleteButton();

    productOptionSetDeleteDialog = new ProductOptionSetDeleteDialog();
    expect(await productOptionSetDeleteDialog.getDialogTitle()).to.eq('resourceApp.productOptionSet.delete.question');
    await productOptionSetDeleteDialog.clickOnConfirmButton();

    expect(await productOptionSetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
