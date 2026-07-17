package tests;

import pages.RegistrationPage;
import io.qameta.allure.*;
import org.testng.Assert; // Наш TestNG асерт!
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

@Feature("Registration Form Tests")
public class RegistrationTest extends BaseTest {

    private RegistrationPage registrationPage;

    @BeforeMethod
    public void initPage() {
        registrationPage = new RegistrationPage();
    }

    @Test(description = "Successful student registration with all fields filled")
    @Severity(SeverityLevel.BLOCKER)
    public void successfulRegistrationTest() {
        String firstName = "Maksym";
        String lastName = "Tsyhanskyi";
        String email = "max.qa@example.com";
        String gender = "Male";
        String mobile = "3809312345";
        String dob = "15/05/1995";
        String subjects = "Maths, Computer Science";
        String hobbies = "Sports, Reading";
        String pictureName = "testing_picture.jpg";
        String address = "Lviv, Ukraine";
        String state = "NCR";
        String city = "Delhi";

        registrationPage.openPage()
                .fillFirstName(firstName)
                .fillLastName(lastName)
                .fillEmail(email)
                .selectGender(gender)
                .fillMobile(mobile)
                .fillDateOfBirth(dob)
                .fillSubjects(subjects)
                .fillHobbies(hobbies)
                .uploadPicture(pictureName)
                .fillCurrentAddress(address)
                .fillState(state, city)
                .clickSubmitButton();

        Map<String, String> expectedResults = Map.ofEntries(
                Map.entry("Student Name", firstName + " " + lastName),
                Map.entry("Student Email", email),
                Map.entry("Gender", gender),
                Map.entry("Mobile", mobile),
                Map.entry("Date of Birth", "15 May,1995"),
                Map.entry("Subjects", subjects),
                Map.entry("Hobbies", hobbies),
                Map.entry("Picture", pictureName),
                Map.entry("Address", address),
                Map.entry("State and City", state + " " + city)
        );

        expectedResults.forEach((key, expectedValue) -> {
            String actualValue = registrationPage.getModalResultValue(key);
            Assert.assertEquals(actualValue, expectedValue,
                    String.format("Помилка у полі '%s'! Очікували: '%s', але отримали: '%s'", key, expectedValue, actualValue));
        });
    }

    @Test(description = "Close modal window button works")
    public void CloseModalWindow() {
        registrationPage.openPage()
                .fillFirstName("Maksym")
                .fillLastName("Tsyhanskyi")
                .selectGender("Male")
                .fillMobile("3809312345")
                .clickSubmitButton();

        registrationPage.pressCloseButton();
        Assert.assertFalse(registrationPage.isModalVisible(), "Modal window should not be visible!");
    }

    @Test(description = "Negative Test: Submit empty form and verify HTML5 validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that student cannot submit empty form, results modal is not shown, and required fields are highlighted in red")
    public void emptyFormValidationTest() {
        registrationPage.openPage()
                .clickSubmitButton();

        String expectedRedColor = "rgb(220, 53, 69)";

        Assert.assertEquals(registrationPage.getFirstNameBorderColor(), expectedRedColor, "First Name border should be red!");
        Assert.assertEquals(registrationPage.getLastNameBorderColor(), expectedRedColor, "Last Name border should be red!");
        Assert.assertEquals(registrationPage.getMobileBorderColor(), expectedRedColor, "Mobile Number border should be red!");

        Assert.assertFalse(registrationPage.isModalVisible(), "Results modal should not be displayed for an empty form submission!");
    }
}