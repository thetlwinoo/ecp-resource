// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { ReviewLinesComponentsPage, ReviewLinesDeleteDialog, ReviewLinesUpdatePage } from './review-lines.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('ReviewLines e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reviewLinesComponentsPage: ReviewLinesComponentsPage;
  let reviewLinesUpdatePage: ReviewLinesUpdatePage;
  let reviewLinesDeleteDialog: ReviewLinesDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ReviewLines', async () => {
    await navBarPage.goToEntity('review-lines');
    reviewLinesComponentsPage = new ReviewLinesComponentsPage();
    await browser.wait(ec.visibilityOf(reviewLinesComponentsPage.title), 5000);
    expect(await reviewLinesComponentsPage.getTitle()).to.eq('resourceApp.reviewLines.home.title');
  });

  it('should load create ReviewLines page', async () => {
    await reviewLinesComponentsPage.clickOnCreateButton();
    reviewLinesUpdatePage = new ReviewLinesUpdatePage();
    expect(await reviewLinesUpdatePage.getPageTitle()).to.eq('resourceApp.reviewLines.home.createOrEditLabel');
    await reviewLinesUpdatePage.cancel();
  });

  it('should create and save ReviewLines', async () => {
    const nbButtonsBeforeCreate = await reviewLinesComponentsPage.countDeleteButtons();

    await reviewLinesComponentsPage.clickOnCreateButton();
    await promise.all([
      reviewLinesUpdatePage.setProductRatingInput('5'),
      reviewLinesUpdatePage.setProductReviewInput('productReview'),
      reviewLinesUpdatePage.setSellerRatingInput('5'),
      reviewLinesUpdatePage.setSellerReviewInput('sellerReview'),
      reviewLinesUpdatePage.setDeliveryRatingInput('5'),
      reviewLinesUpdatePage.setDeliveryReviewInput('deliveryReview'),
      reviewLinesUpdatePage.setPhotoInput(absolutePath),
      reviewLinesUpdatePage.setLastEditedByInput('lastEditedBy'),
      reviewLinesUpdatePage.setLastEditedWhenInput('2000-12-31'),
      reviewLinesUpdatePage.reviewSelectLastOption()
    ]);
    expect(await reviewLinesUpdatePage.getProductRatingInput()).to.eq('5', 'Expected productRating value to be equals to 5');
    expect(await reviewLinesUpdatePage.getProductReviewInput()).to.eq(
      'productReview',
      'Expected ProductReview value to be equals to productReview'
    );
    expect(await reviewLinesUpdatePage.getSellerRatingInput()).to.eq('5', 'Expected sellerRating value to be equals to 5');
    expect(await reviewLinesUpdatePage.getSellerReviewInput()).to.eq(
      'sellerReview',
      'Expected SellerReview value to be equals to sellerReview'
    );
    expect(await reviewLinesUpdatePage.getDeliveryRatingInput()).to.eq('5', 'Expected deliveryRating value to be equals to 5');
    expect(await reviewLinesUpdatePage.getDeliveryReviewInput()).to.eq(
      'deliveryReview',
      'Expected DeliveryReview value to be equals to deliveryReview'
    );
    expect(await reviewLinesUpdatePage.getPhotoInput()).to.endsWith(
      fileNameToUpload,
      'Expected Photo value to be end with ' + fileNameToUpload
    );
    expect(await reviewLinesUpdatePage.getLastEditedByInput()).to.eq(
      'lastEditedBy',
      'Expected LastEditedBy value to be equals to lastEditedBy'
    );
    expect(await reviewLinesUpdatePage.getLastEditedWhenInput()).to.eq(
      '2000-12-31',
      'Expected lastEditedWhen value to be equals to 2000-12-31'
    );
    await reviewLinesUpdatePage.save();
    expect(await reviewLinesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await reviewLinesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ReviewLines', async () => {
    const nbButtonsBeforeDelete = await reviewLinesComponentsPage.countDeleteButtons();
    await reviewLinesComponentsPage.clickOnLastDeleteButton();

    reviewLinesDeleteDialog = new ReviewLinesDeleteDialog();
    expect(await reviewLinesDeleteDialog.getDialogTitle()).to.eq('resourceApp.reviewLines.delete.question');
    await reviewLinesDeleteDialog.clickOnConfirmButton();

    expect(await reviewLinesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
