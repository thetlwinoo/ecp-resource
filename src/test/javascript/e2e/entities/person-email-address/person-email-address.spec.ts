// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {
  PersonEmailAddressComponentsPage,
  PersonEmailAddressDeleteDialog,
  PersonEmailAddressUpdatePage
} from './person-email-address.page-object';

const expect = chai.expect;

describe('PersonEmailAddress e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let personEmailAddressComponentsPage: PersonEmailAddressComponentsPage;
  let personEmailAddressUpdatePage: PersonEmailAddressUpdatePage;
  let personEmailAddressDeleteDialog: PersonEmailAddressDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PersonEmailAddresses', async () => {
    await navBarPage.goToEntity('person-email-address');
    personEmailAddressComponentsPage = new PersonEmailAddressComponentsPage();
    await browser.wait(ec.visibilityOf(personEmailAddressComponentsPage.title), 5000);
    expect(await personEmailAddressComponentsPage.getTitle()).to.eq('resourceApp.personEmailAddress.home.title');
  });

  it('should load create PersonEmailAddress page', async () => {
    await personEmailAddressComponentsPage.clickOnCreateButton();
    personEmailAddressUpdatePage = new PersonEmailAddressUpdatePage();
    expect(await personEmailAddressUpdatePage.getPageTitle()).to.eq('resourceApp.personEmailAddress.home.createOrEditLabel');
    await personEmailAddressUpdatePage.cancel();
  });

  it('should create and save PersonEmailAddresses', async () => {
    const nbButtonsBeforeCreate = await personEmailAddressComponentsPage.countDeleteButtons();

    await personEmailAddressComponentsPage.clickOnCreateButton();
    await promise.all([
      personEmailAddressUpdatePage.setEmailAddressInput('emailAddress'),
      personEmailAddressUpdatePage.personSelectLastOption()
    ]);
    expect(await personEmailAddressUpdatePage.getEmailAddressInput()).to.eq(
      'emailAddress',
      'Expected EmailAddress value to be equals to emailAddress'
    );
    const selectedDefaultInd = personEmailAddressUpdatePage.getDefaultIndInput();
    if (await selectedDefaultInd.isSelected()) {
      await personEmailAddressUpdatePage.getDefaultIndInput().click();
      expect(await personEmailAddressUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd not to be selected').to.be.false;
    } else {
      await personEmailAddressUpdatePage.getDefaultIndInput().click();
      expect(await personEmailAddressUpdatePage.getDefaultIndInput().isSelected(), 'Expected defaultInd to be selected').to.be.true;
    }
    const selectedActiveInd = personEmailAddressUpdatePage.getActiveIndInput();
    if (await selectedActiveInd.isSelected()) {
      await personEmailAddressUpdatePage.getActiveIndInput().click();
      expect(await personEmailAddressUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd not to be selected').to.be.false;
    } else {
      await personEmailAddressUpdatePage.getActiveIndInput().click();
      expect(await personEmailAddressUpdatePage.getActiveIndInput().isSelected(), 'Expected activeInd to be selected').to.be.true;
    }
    await personEmailAddressUpdatePage.save();
    expect(await personEmailAddressUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await personEmailAddressComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PersonEmailAddress', async () => {
    const nbButtonsBeforeDelete = await personEmailAddressComponentsPage.countDeleteButtons();
    await personEmailAddressComponentsPage.clickOnLastDeleteButton();

    personEmailAddressDeleteDialog = new PersonEmailAddressDeleteDialog();
    expect(await personEmailAddressDeleteDialog.getDialogTitle()).to.eq('resourceApp.personEmailAddress.delete.question');
    await personEmailAddressDeleteDialog.clickOnConfirmButton();

    expect(await personEmailAddressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
