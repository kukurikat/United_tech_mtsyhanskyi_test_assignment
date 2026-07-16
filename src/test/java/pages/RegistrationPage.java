package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage {

    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement userEmailInput = $("#userEmail");
    private final SelenideElement mobileNumberInput = $("#userNumber");
    private final SelenideElement dateOfBirthInput = $("#dateOfBirthInput");
    private final SelenideElement monthSelect =  $(".react-datepicker__month-select");
    private final SelenideElement yearSelect = $(".react-datepicker__year-select");
    private final SelenideElement subjectsInput = $("#subjectsInput");
    private final SelenideElement uploadPictureInput = $("#uploadPicture");
    private final SelenideElement currentAddressInput = $("#currentAddress");
    private final SelenideElement stateDropdown = $("#state");
    private final SelenideElement cityDropdown = $("#city");
    private final SelenideElement stateInput = $("#react-select-3-input");
    private final SelenideElement cityInput = $("#react-select-4-input");
    private final SelenideElement submitButton = $("#submit");
    private final SelenideElement resultsModal = $(".modal-content");
    private final SelenideElement resultsTable = $(".table-responsive");
    private final SelenideElement closeModalButton = $(".closeLargeModal");

    private SelenideElement getGenderLabel(String gender) {
        return $("#genterWrapper").$(byText(gender));
    }

    private SelenideElement getHobbiesLabel(String hobbie) {
        return $("#hobbiesWrapper").$(byText(hobbie));
    }
    @Step("Open registration page")
    public RegistrationPage openPage() {
        Selenide.open("/automation-practice-form");
        return this;
    }

    @Step("Enter first name : {firstName}")
    public RegistrationPage fillFirstName(String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    @Step("Enter last name: {lastName}")
    public RegistrationPage fillLastName(String lastName) {
        lastNameInput.setValue(lastName);
        return this;
    }

    @Step("enter email: {email}")
    public RegistrationPage fillEmail(String email) {
        userEmailInput.setValue(email);
        return this;
    }

    @Step ("Select gender (Male, Female, Other): {gender}")
    public RegistrationPage selectGender(String gender) {
        getGenderLabel(gender).click();
        return this;
    }

    @Step("Enter mobile: {mobile}")
    public RegistrationPage fillMobile(String mobile) {
        mobileNumberInput.setValue(mobile);
        return this;
    }

    @Step("Enter date of birth(e.g. 01/05/1990): {dateOfBirth}")
    public RegistrationPage fillDateOfBirth(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dateOfBirth, formatter);

        int day = localDate.getDayOfMonth();
        String monthName = localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String year = String.valueOf(localDate.getYear());

        dateOfBirthInput.click();
        monthSelect.selectOption(monthName);
        yearSelect.selectOption(year);

        String dayClassValue = String.format("%03d", day);

        String daySelector = String.format(".react-datepicker__day--%s:not(.react-datepicker__day--outside-month)", dayClassValue);
        $(daySelector).click();

        return this;
    }

    @Step("Enter subjects: {subjectsString}")
    public RegistrationPage fillSubjects(String subjectsString) {
        if (subjectsString == null || subjectsString.trim().isEmpty()) {
            return this;
        }
        String[] subjects = subjectsString.split("\\s*,\\s*");
        Arrays.stream(subjects).forEach(subject -> {
            subjectsInput.setValue(subject).pressEnter();
        });

        return this;
    }

    @Step("Select hobbies (Sports, Reading, Music)")
    public RegistrationPage fillHobbies(String hobbies) {
        String[] hobbiesArray = hobbies.split("\\s*,\\s*");
        Arrays.stream(hobbiesArray).forEach(hobbie -> {getHobbiesLabel(hobbie).click();});
        return this;
    }

    @Step("Upload picture")
    public RegistrationPage uploadPicture(String uploadPicture) {
        uploadPictureInput.uploadFromClasspath("testing_picture.jpg");
        return this;
    }

    @Step("Enter current address: {address}")
    public RegistrationPage fillCurrentAddress(String address) {
        currentAddressInput.setValue(address);
        return this;
    }

    @Step("Choose state: {state} and city: {city}")
    public RegistrationPage fillState(String state, String city) {
        stateDropdown.click();
        stateInput.setValue(state).pressEnter();
        cityDropdown.click();
        cityInput.setValue(city).pressEnter();

        return this;
    }

    @Step("Click submit button")
    public RegistrationPage clickSubmitButton() {
        submitButton.click();
        return this;
    }

    @Step("Get modal result value for field: {key}")
    public String getModalResultValue(String key) {
        // Переконуємося, що модалка видима
        resultsModal.shouldBe(Condition.visible);
        // Повертаємо текст правої комірки для нашого поля
        return resultsTable.$(byText(key)).sibling(0).text();
    }

    @Step("Verify that modal is closed")
    public boolean isModalVisible() {
        return resultsModal.is(Condition.visible);
    }

    @Step("Press close modal button")
    public RegistrationPage pressCloseButton() {
        closeModalButton.scrollTo();
        Selenide.executeJavaScript("arguments[0].click();", closeModalButton);
        return this;
    }

    @Step("Get border color of First Name field")
    public String getFirstNameBorderColor() {
        return firstNameInput.getCssValue("border-color");
    }

    @Step("Get border color of Last Name field")
    public String getLastNameBorderColor() {
        return lastNameInput.getCssValue("border-color");
    }

    @Step("Get border color of Mobile Number field")
    public String getMobileBorderColor() {
        return mobileNumberInput.getCssValue("border-color");
    }
}