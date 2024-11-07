package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.BagPage;
import pageobjects.ProductDisplayPage;
import stepdefs.hooks.Hooks;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class BagStepDefs {

  private final WebDriver driver;
  private Long productId;

  public BagStepDefs(){
    this.driver = Hooks.getDriver();
  }

  @Given("there are products in the bag")
  public void theUserHasAProductInTheBag() {
    driver.get("https://uk.gymshark.com/products/gymshark-speed-t-shirt-black-aw23");
    productId = 39654522814667L;
    ProductDisplayPage productDisplayPage = new ProductDisplayPage();
    productDisplayPage.selectSmallSize();
    productDisplayPage.selectAddToBag();
  }

  @When("I remove a product")
  public void removingAProductFromTheBag() {
    BagPage bagPage = new BagPage();
    bagPage.removeProductFromBag(productId);
  }

  @Then("the product is removed from the bag")
  public void theProductShouldDisappearFromTheBag() {
    BagPage bagPage = new BagPage();
    assertThat(bagPage.isBagEmpty()).as("Expected product is not in Bag");
  }

  @When("I add quantity")
  public void addingQuantityToAProductInTheBag() {
    BagPage bagPage = new BagPage();
    bagPage.changeProductQuantity(productId, 3);
  }

  @Then("product quantity is increased")
  public void theProductQuantityShouldIncreaseInTheBag() {
    BagPage bagPage = new BagPage();
    assertThat(Objects.equals(bagPage.getVariantQuantity(productId), "QTY: 3")).as("Expected quantity of product is in Bag");
  }

  @When("I remove quantity")
  public void removingQuantityFromAProductInTheBag() {
    addingQuantityToAProductInTheBag();
    BagPage bagPage = new BagPage();
    bagPage.changeProductQuantity(productId, 2);
  }

  @Then("product quantity is removed from the bag")
  public void theProductQuantityShouldDecreaseInTheBag() {
    BagPage bagPage = new BagPage();
    assertThat(Objects.equals(bagPage.getVariantQuantity(productId), "QTY: 2")).as("Expected quantity of product is in Bag");
  }
}
