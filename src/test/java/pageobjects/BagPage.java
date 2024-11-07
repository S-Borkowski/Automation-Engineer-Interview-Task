package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

import static utils.SeleniumCommands.getCommands;
import static utils.StringUtils.extractVariantIDFromString;

public class BagPage {

  private static final By BAG_PAGE = By.cssSelector("[data-locator-id='miniBag-component']");
  private static final By BAG_ITEMS = By.cssSelector("[data-locator-id^='miniBag-miniBagItem']");
  private static final By EMPTY_BAG = By.cssSelector("[data-locator-id^='miniBag-miniBagEmpty']");
  public static final String GS_LOCATOR_ATTRIBUTE = "data-locator-id";
  public static final By QUANTITY_TEXTBOX = By.cssSelector("[class^='qty-selector_text_']");
  public static final By REMOVE_PRODUCT_BUTTON = By.cssSelector("[data-locator-id^='miniBag-remove-']");

  public BagPage() {
    getCommands().waitForAndGetVisibleElementLocated(BAG_PAGE);
  }

  public List<Long> getVariantIdsInBag() {
    return getBagItems().stream()
      .map(this::getVariantId)
      .collect(Collectors.toList());
  }

  private List<WebElement> getBagItems() {
    return getCommands().waitForAndGetAllVisibleElementsLocated(BAG_ITEMS);
  }

  private WebElement getBagItemByVariantId(long variantId) {
    return getBagItems().stream()
      .filter(bagItem -> getVariantId(bagItem) == variantId)
      .findFirst()
      .orElse(null);
  }

  private long getVariantId(WebElement bagItem) {
    return extractVariantIDFromString(getCommands().getAttributeFromElement(bagItem, GS_LOCATOR_ATTRIBUTE));
  }

  public String getVariantQuantity(long variantId) {
    return getCommands().getTextFromElement(
            getBagItemByVariantId(variantId).findElement(QUANTITY_TEXTBOX)
    );
  }

  public BagPage removeProductFromBag(long variantId) {
    getBagItemByVariantId(variantId).findElement(REMOVE_PRODUCT_BUTTON).click();
    return this;
  }

  public BagPage changeProductQuantity(long variantId, int quantity) {
    getBagItemByVariantId(variantId).findElement(
            By.cssSelector("[data-locator-id^='miniBag-quantityDropdown-'] option:nth-child(" + quantity + ")")
    ).click();
    return this;
  }

  public boolean isBagEmpty() {
    return getCommands().waitForAndGetVisibleElementLocated(EMPTY_BAG) != null;
  }
}
