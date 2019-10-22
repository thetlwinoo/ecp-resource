// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ReviewsComponentsPage, ReviewsDeleteDialog, ReviewsUpdatePage } from './reviews.page-object';

const expect = chai.expect;

describe('Reviews e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reviewsComponentsPage: ReviewsComponentsPage;
  let reviewsUpdatePage: ReviewsUpdatePage;
  let reviewsDeleteDialog: ReviewsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Reviews', async () => {
    await navBarPage.goToEntity('reviews');
    reviewsComponentsPage = new ReviewsComponentsPage();
    await browser.wait(ec.visibilityOf(reviewsComponentsPage.title), 5000);
    expect(await reviewsComponentsPage.getTitle()).to.eq('resourceApp.reviews.home.title');
  });

  it('should load create Reviews page', async () => {
    await reviewsComponentsPage.clickOnCreateButton();
    reviewsUpdatePage = new ReviewsUpdatePage();
    expect(await reviewsUpdatePage.getPageTitle()).to.eq('resourceApp.reviews.home.createOrEditLabel');
    await reviewsUpdatePage.cancel();
  });

  it('should create and save Reviews', async () => {
    const nbButtonsBeforeCreate = await reviewsComponentsPage.countDeleteButtons();

    await reviewsComponentsPage.clickOnCreateButton();
    await promise.all([
      reviewsUpdatePage.setReviewerNameInput('reviewerName'),
      reviewsUpdatePage.setEmailAddressInput('emailAddress'),
      reviewsUpdatePage.setReviewDateInput('2000-12-31'),
      reviewsUpdatePage.setOverAllSellerRatingInput('5'),
      reviewsUpdatePage.setOverAllSellerReviewInput('overAllSellerReview'),
      reviewsUpdatePage.setOverAllDeliveryRatingInput('5'),
      reviewsUpdatePage.setOverAllDeliveryReviewInput('overAllDeliveryReview'),
      reviewsUpdatePage.setLastEditedByInput('lastEditedBy'),
      reviewsUpdatePage.setLastEditedWhenInput('2000-12-31')
    ]);
    expect(await reviewsUpdatePage.getReviewerNameInput()).to.eq(
      'reviewerName',
      'Expected ReviewerName value to be equals to reviewerName'
    );
    expect(await reviewsUpdatePage.getEmailAddressInput()).to.eq(
      'emailAddress',
      'Expected EmailAddress value to be equals to emailAddress'
    );
    expect(await reviewsUpdatePage.getReviewDateInput()).to.eq('2000-12-31', 'Expected reviewDate value to be equals to 2000-12-31');
    expect(await reviewsUpdatePage.getOverAllSellerRatingInput()).to.eq('5', 'Expected overAllSellerRating value to be equals to 5');
    expect(await reviewsUpdatePage.getOverAllSellerReviewInput()).to.eq(
      'overAllSellerReview',
      'Expected OverAllSellerReview value to be equals to overAllSellerReview'
    );
    expect(await reviewsUpdatePage.getOverAllDeliveryRatingInput()).to.eq('5', 'Expected overAllDeliveryRating value to be equals to 5');
    expect(await reviewsUpdatePage.getOverAllDeliveryReviewInput()).to.eq(
      'overAllDeliveryReview',
      'Expected OverAllDeliveryReview value to be equals to overAllDeliveryReview'
    );
    const selectedReviewAsAnonymous = reviewsUpdatePage.getReviewAsAnonymousInput();
    if (await selectedReviewAsAnonymous.isSelected()) {
      await reviewsUpdatePage.getReviewAsAnonymousInput().click();
      expect(await reviewsUpdatePage.getReviewAsAnonymousInput().isSelected(), 'Expected reviewAsAnonymous not to be selected').to.be.false;
    } else {
      await reviewsUpdatePage.getReviewAsAnonymousInput().click();
      expect(await reviewsUpdatePage.getReviewAsAnonymousInput().isSelected(), 'Expected reviewAsAnonymous to be selected').to.be.true;
    }
    const selectedCompletedReview = reviewsUpdatePage.getCompletedReviewInput();
    if (await selectedCompletedReview.isSelected()) {
      await reviewsUpdatePage.getCompletedReviewInput().click();
      expect(await reviewsUpdatePage.getCompletedReviewInput().isSelected(), 'Expected completedReview not to be selected').to.be.false;
    } else {
      await reviewsUpdatePage.getCompletedReviewInput().click();
      expect(await reviewsUpdatePage.getCompletedReviewInput().isSelected(), 'Expected completedReview to be selected').to.be.true;
    }
    expect(await reviewsUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await reviewsUpdatePage.getLastEditedWhenInput()).to.eq(
      '2000-12-31',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await reviewsUpdatePage.save();
    expect(await reviewsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await reviewsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Reviews', async () => {
    const nbButtonsBeforeDelete = await reviewsComponentsPage.countDeleteButtons();
    await reviewsComponentsPage.clickOnLastDeleteButton();

    reviewsDeleteDialog = new ReviewsDeleteDialog();
    expect(await reviewsDeleteDialog.getDialogTitle()).to.eq('resourceApp.reviews.delete.question');
    await reviewsDeleteDialog.clickOnConfirmButton();

    expect(await reviewsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
