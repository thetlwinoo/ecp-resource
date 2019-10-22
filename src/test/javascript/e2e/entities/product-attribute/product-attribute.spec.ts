// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ProductAttributeComponentsPage, ProductAttributeDeleteDialog, ProductAttributeUpdatePage } from './product-attribute.page-object';

const expect = chai.expect;

describe('ProductAttribute e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productAttributeComponentsPage: ProductAttributeComponentsPage;
  let productAttributeUpdatePage: ProductAttributeUpdatePage;
  let productAttributeDeleteDialog: ProductAttributeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductAttributes', async () => {
    await navBarPage.goToEntity('product-attribute');
    productAttributeComponentsPage = new ProductAttributeComponentsPage();
    await browser.wait(ec.visibilityOf(productAttributeComponentsPage.title), 5000);
    expect(await productAttributeComponentsPage.getTitle()).to.eq('resourceApp.productAttribute.home.title');
  });

  it('should load create ProductAttribute page', async () => {
    await productAttributeComponentsPage.clickOnCreateButton();
    productAttributeUpdatePage = new ProductAttributeUpdatePage();
    expect(await productAttributeUpdatePage.getPageTitle()).to.eq('resourceApp.productAttribute.home.createOrEditLabel');
    await productAttributeUpdatePage.cancel();
  });

  it('should create and save ProductAttributes', async () => {
    const nbButtonsBeforeCreate = await productAttributeComponentsPage.countDeleteButtons();

    await productAttributeComponentsPage.clickOnCreateButton();
    await promise.all([
      productAttributeUpdatePage.setProductAttributeValueInput('productAttributeValue'),
      productAttributeUpdatePage.productAttributeSetSelectLastOption(),
      productAttributeUpdatePage.supplierSelectLastOption()
    ]);
    expect(await productAttributeUpdatePage.getProductAttributeValueInput()).to.eq(
      'productAttributeValue',
      'Expected ProductAttributeValue value to be equals to productAttributeValue'
    );
    await productAttributeUpdatePage.save();
    expect(await productAttributeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productAttributeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductAttribute', async () => {
    const nbButtonsBeforeDelete = await productAttributeComponentsPage.countDeleteButtons();
    await productAttributeComponentsPage.clickOnLastDeleteButton();

    productAttributeDeleteDialog = new ProductAttributeDeleteDialog();
    expect(await productAttributeDeleteDialog.getDialogTitle()).to.eq('resourceApp.productAttribute.delete.question');
    await productAttributeDeleteDialog.clickOnConfirmButton();

    expect(await productAttributeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
