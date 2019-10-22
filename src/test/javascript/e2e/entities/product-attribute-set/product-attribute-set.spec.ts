// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  ProductAttributeSetComponentsPage,
  ProductAttributeSetDeleteDialog,
  ProductAttributeSetUpdatePage
} from './product-attribute-set.page-object';

const expect = chai.expect;

describe('ProductAttributeSet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productAttributeSetComponentsPage: ProductAttributeSetComponentsPage;
  let productAttributeSetUpdatePage: ProductAttributeSetUpdatePage;
  let productAttributeSetDeleteDialog: ProductAttributeSetDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductAttributeSets', async () => {
    await navBarPage.goToEntity('product-attribute-set');
    productAttributeSetComponentsPage = new ProductAttributeSetComponentsPage();
    await browser.wait(ec.visibilityOf(productAttributeSetComponentsPage.title), 5000);
    expect(await productAttributeSetComponentsPage.getTitle()).to.eq('resourceApp.productAttributeSet.home.title');
  });

  it('should load create ProductAttributeSet page', async () => {
    await productAttributeSetComponentsPage.clickOnCreateButton();
    productAttributeSetUpdatePage = new ProductAttributeSetUpdatePage();
    expect(await productAttributeSetUpdatePage.getPageTitle()).to.eq('resourceApp.productAttributeSet.home.createOrEditLabel');
    await productAttributeSetUpdatePage.cancel();
  });

  it('should create and save ProductAttributeSets', async () => {
    const nbButtonsBeforeCreate = await productAttributeSetComponentsPage.countDeleteButtons();

    await productAttributeSetComponentsPage.clickOnCreateButton();
    await promise.all([
      productAttributeSetUpdatePage.setProductAttributeSetNameInput('productAttributeSetName'),
      productAttributeSetUpdatePage.productOptionSetSelectLastOption()
    ]);
    expect(await productAttributeSetUpdatePage.getProductAttributeSetNameInput()).to.eq(
      'productAttributeSetName',
      'Expected ProductAttributeSetName value to be equals to productAttributeSetName'
    );
    await productAttributeSetUpdatePage.save();
    expect(await productAttributeSetUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productAttributeSetComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductAttributeSet', async () => {
    const nbButtonsBeforeDelete = await productAttributeSetComponentsPage.countDeleteButtons();
    await productAttributeSetComponentsPage.clickOnLastDeleteButton();

    productAttributeSetDeleteDialog = new ProductAttributeSetDeleteDialog();
    expect(await productAttributeSetDeleteDialog.getDialogTitle()).to.eq('resourceApp.productAttributeSet.delete.question');
    await productAttributeSetDeleteDialog.clickOnConfirmButton();

    expect(await productAttributeSetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
