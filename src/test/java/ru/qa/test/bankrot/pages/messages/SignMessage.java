package ru.qa.test.bankrot.pages.messages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.qa.test.bankrot.appmanager.HelperBase;

public class SignMessage extends HelperBase {

  private String baseName = "ctl00_ctl00_ctplhMain_CentralContentPlaceHolder_ucSignMessage_";
  private By signButton = By.id(baseName+"DigitalSignature1_SignImageButtonInput");
  private By confirmCertificate = By.xpath(".//button[@data-element-type='select-button']");
  private By saveButton = By.id("SaveButton");
  private By gotoMessagesList = By.cssSelector("li.last > a");
  private By certificateLocator;


  public SignMessage(WebDriver wd, WebDriverWait wait, Actions actions, String certificateName) {
    super(wd, wait, actions);
    this.certificateLocator = By.xpath("//h4[contains(., "+certificateName+")]");
  }

  public void signMessage() throws InterruptedException {
    sign();
    gotoMessagesListPage();
  }

  public void signMessageAndReturnMessList() throws InterruptedException {
    sign();
    gotoMessagesListPage();
    wd.get("http://bankruptcytest.devel.ifx/BackOffice/ArbitrManager/MessagesList.aspx");
  }

  public void signAndPayMessage() throws InterruptedException {
    sign();
    payFromPersonalAccount();
  }

  public void sign() throws InterruptedException {
    сallDigitalSignature();
    selectCertificate();
    confirmChoiceCertificate();
    closeAlert("ЭЦП создана.\n" + "Нажмите кнопку 'Сохранить'.");
    clickSaveButton();
  }

  @Step("нажать кнопку 'Подписать'")
  public void сallDigitalSignature() {
    click(signButton);
  }

  @Step("выбрать сертификат")
  public void selectCertificate() throws InterruptedException {
    Thread.sleep(2000);
    wait.until(ExpectedConditions.visibilityOfElementLocated(certificateLocator));
    click(certificateLocator);
  }

  @Step("нажать кнопку 'ОК' в окне со списком сертификатов")
  public void confirmChoiceCertificate() {
    click(confirmCertificate);
  }

  @Step("нажать кнопку 'Сохранить'")
  public void clickSaveButton() {
    click(saveButton);
  }

  @Step("щелкнуть на ссылке 'Перейти в список сообщений'")
  public void gotoMessagesListPage() throws InterruptedException {
    Thread.sleep(200);
    click(gotoMessagesList);
  }

  @Step("оплатить публикацию сообщения с лицевого счета")
  public void payFromPersonalAccount() throws InterruptedException {
    click(By.linkText("Оплатить публикацию с лицевого счета"));
    wd.switchTo().frame(0);
    click(By.id("ctl00_cplhContent_ucMessagePayment_btnNext"));
    click(By.id("ctl00_cplhContent_ucPayOrderFromAccount_ibSave"));
    closeAlertPay();
  }


}
