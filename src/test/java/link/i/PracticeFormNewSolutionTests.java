package link.i;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PracticeFormNewSolutionTests {

    String firstname = "Ivan";
    String lastName = "Ivanov";
    String userEmail = "Ivanov@mail.com";
    String numberEnter = "1234567890123";//ввести можно сколько угодно символов
    String numberEntered = "1234567890"; //форма принимает 10 символов
    String pictureName = "7777.png";
    String currentAddress = "Tushkina city, Kolotushkina street";
    String subject = "his";
    int monthNumber = 1;
    int yearNumber = 1;

    //Кликаем на элемент
    void clickOnElement(String locator) {
        $(locator).click();
    }
    //Селектор имеет состояние
    void elementShouldBe(String locator, Condition condition) {
        $(locator).shouldBe(condition);
    }
    //Селектор содержит состояние
    void elementShouldHave(String locator, Condition condition) {
        $(locator).shouldHave(condition);
    }
    //Задаём значение и проверяем, что оно внеслось
    void setValueAndCheck(String locator, String value) {
        $(locator).setValue(value).shouldHave(value(value));
    }
    //Получаем текст элемента и возвращаем
    String getElementText(String locator) {
        String elementText = $(locator).getText();
        return elementText;
    }
    // Выбираем элемент из дропдауна и возвращаем текст в нём
    String chooseDropdownElement (String dropdown, int value){
        String elementText = $(dropdown).$("option", value).getText();
        $(dropdown).$("option", value).click();
        return elementText;
    }
    // ищем значение в таблице и возвращаем элемент селенида для дальнейшей проверки
        SelenideElement checkValueOnTable (String rowTitle){
            SelenideElement elem = $("tbody").find(byTagAndText("td", rowTitle)).sibling(0);
        return elem;
    }

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1500x1080";
    }

    @Test
    void fillFormsWithPositiveValues() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        //executeJavaScript("$'footer'.remove()");

        setValueAndCheck("#firstName", firstname);

        setValueAndCheck("#lastName", lastName);

        setValueAndCheck("#userEmail", userEmail);

        $("#gender-radio-1").parent().click();

        elementShouldBe("input[id=gender-radio-1]", checked);

        $("$genterWrapper").$(byText("Female")).click();

        elementShouldBe("input[id=gender-radio-2]", checked);

        clickOnElement("label[for=gender-radio-3]");

        String gender = getElementText("label[for=gender-radio-3]");

        elementShouldBe("input[id=gender-radio-3]", checked);

        $("#userNumber").setValue(numberEnter).shouldHave(value(numberEntered));

        clickOnElement("#dateOfBirthInput");

        elementShouldBe(".react-datepicker__month-container", visible);
        $(".react-datepicker__month-select").selectOption("July");
        $(".react-datepicker__year-select").selectOption("2008");
        $(".react-datepicker__day react-datepicker__day--030:not(react-datepicker__day--outside-month)").selectOption("2008");

//        clickOnElement("select[class=react-datepicker__month-select]");
//
//        String month = chooseDropdownElement("select[class=react-datepicker__month-select]", monthNumber);
//
//        clickOnElement("select[class=react-datepicker__year-select]");
//
//        String year = chooseDropdownElement("select[class=react-datepicker__year-select]", yearNumber);

        String day = $(".react-datepicker__week").lastChild().getText();
        $(".react-datepicker__week").lastChild().click();

        $("#dateOfBirthInput").shouldHave(
                value(day)
//                value(month.substring(0,3)),
//                value(year)
        );
        $(".react-datepicker__month-container").shouldNotBe(visible);

        setValueAndCheck("#subjectsInput", subject);

        String chosenSubject = getElementText("#react-select-2-option-0");
        clickOnElement("#react-select-2-option-0");

//        clickOnElement("label[for=hobbies-checkbox-1]");
//        String hobby1 = getElementText("label[for=hobbies-checkbox-1");
//        elementShouldBe("input[id=hobbies-checkbox-1]", checked);
        $("#hobbiesWrapper").$(byText("Sport")).click();

        clickOnElement("label[for=hobbies-checkbox-2]");
        String hobby2 = getElementText("label[for=hobbies-checkbox-2");
        elementShouldBe("input[id=hobbies-checkbox-2]", checked);

        clickOnElement("label[for=hobbies-checkbox-3]");
        String hobby3 = getElementText("label[for=hobbies-checkbox-3");
        elementShouldBe("input[id=hobbies-checkbox-3]", checked);

        $("#uploadPicture").uploadFromClasspath(pictureName);
//        $("#uploadPicture").uploadFile(new File("src/test/resources/img/file.jpg"));
        $("#uploadPicture").shouldHave(value(pictureName));

        setValueAndCheck("#currentAddress",currentAddress);

        clickOnElement("#state");
//        String state = getElementText("#react-select-3-option-2");
//        clickOnElement("#react-select-3-option-2");
//        elementShouldHave(".css-1uccc91-singleValue", text(state));
        $("#stateCity-wrapper").$(byText("NCR")).click();


        clickOnElement("#city");
//        String city = getElementText("#react-select-4-option-0");
//        clickOnElement("#react-select-4-option-0");
//        $(".css-1uccc91-singleValue").shouldHave(text(state));
        $("#stateCity-wrapper").$(byText("Delhi")).click();

        clickOnElement("#submit");

        //Проверяем, что все данные ушли правильно
        $(".modal-content").should(exist);

        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $("#table-responsive").shouldHave(
                text(firstname),
                text(lastName),
                text(userEmail),
                text("Male"),
                text(numberEntered),
                text("Math"));
        $("#table-responsive").$(byText("Student Name"))
                .parent().shouldHave(text(firstname), text(lastName));

//        checkValueOnTable( "Student Name").shouldHave(text(firstname), text(lastName));
//
//        checkValueOnTable("Student Email").shouldHave(text(userEmail));
//
//        checkValueOnTable("Gender").shouldHave(text(gender));
//
//        checkValueOnTable("Mobile").shouldHave(text(numberEntered));
//
//        checkValueOnTable("Date of Birth").shouldHave(text(day), text(month), text(year));
//
//        checkValueOnTable("Subjects").shouldHave(text(chosenSubject));
//
//        checkValueOnTable("Hobbies").shouldHave(text(hobby1), text(hobby2), text(hobby3));
//
//        checkValueOnTable("Picture").shouldHave(text(pictureName));
//
//        checkValueOnTable("Address").shouldHave(text(currentAddress));
//
//        checkValueOnTable("State and City").shouldHave(text(state), text(city));

        clickOnElement("#closeLargeModal");
        $(".modal-content").shouldNot(exist);
    }
}
