package link.i;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PracticeFormTests {

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

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1500x1080";
    }

    @Test
    void fillFormsWithPositiveValues() {
        open("/automation-practice-form");

        $("#firstName").setValue(firstname).shouldHave(value(firstname));

        $("#lastName").setValue(lastName).shouldHave(value(lastName));

        $("#userEmail").setValue(userEmail).shouldHave(value(userEmail));

        $("label[for=gender-radio-1]").click();
        $("input[id=gender-radio-1]").shouldBe(Condition.checked);
        $("label[for=gender-radio-2]").click();
        $("input[id=gender-radio-2]").shouldBe(Condition.checked);
        $("label[for=gender-radio-3]").click();
        String gender = $("label[for=gender-radio-3]").getText();
        $("input[id=gender-radio-3]").shouldBe(Condition.checked);

        $("#userNumber").setValue(numberEnter).shouldHave(value(numberEntered));

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-container").shouldBe(visible);

        $("select[class=react-datepicker__month-select]").click();
        String month = $("select[class=react-datepicker__month-select]")
                .$("option", monthNumber).getText();
        $("select[class=react-datepicker__month-select]")
                .$("option", monthNumber).click();

        $("select[class=react-datepicker__year-select]").click();
        String year = $("select[class=react-datepicker__year-select]")
                .$("option", yearNumber).getText();
        $("select[class=react-datepicker__year-select]")
                .$("option", yearNumber).click();

        String day = $(".react-datepicker__week").lastChild().getText();
        $(".react-datepicker__week").lastChild().click();

        $("#dateOfBirthInput").shouldHave(
                value(day),
                value(month.substring(0,3)),
                value(year)
        );
        $(".react-datepicker__month-container").shouldNotBe(visible);

        $("#subjectsInput").setValue(subject);
        String chosenSubject = $("#react-select-2-option-0").getText();
        $("#react-select-2-option-0").click();

        $("label[for=hobbies-checkbox-1]").click();
        String hobby1 = $("label[for=hobbies-checkbox-1").getText();
        $("input[id=hobbies-checkbox-1]").shouldBe(Condition.checked);
        $("label[for=hobbies-checkbox-2]").click();
        String hobby2 = $("label[for=hobbies-checkbox-2").getText();
        $("input[id=hobbies-checkbox-2]").shouldBe(Condition.checked);
        $("label[for=hobbies-checkbox-3]").click();
        String hobby3 = $("label[for=hobbies-checkbox-3").getText();
        $("input[id=hobbies-checkbox-3]").shouldBe(Condition.checked);

        $("#uploadPicture").uploadFromClasspath(pictureName);
        $("#uploadPicture").shouldHave(value(pictureName));

        $("#currentAddress").setValue(currentAddress).shouldHave(value(currentAddress));

        $("#state").click();
        String state = $("#react-select-3-option-2").getText();
        $("#react-select-3-option-2").click();
        $(".css-1uccc91-singleValue").shouldHave(text(state));

        $("#city").click();
        String city = $("#react-select-4-option-0").getText();
        $("#react-select-4-option-0").click();
        $(".css-1uccc91-singleValue").shouldHave(text(state));

        $("#submit").click();

        $(".modal-content").should(exist);

        $("tbody").find("tr").shouldHave(text("Student Name"))
                .lastChild()
                .shouldHave(text(firstname), text(lastName));
        $("tbody").find(byTagAndText("td", "Student Name"))
                .sibling(0)
                .shouldHave(text(firstname), text(lastName));
        $("tbody").find(byTagAndText("td", "Student Email"))
                .sibling(0)
                .shouldHave(text(userEmail));
        $("tbody").find(byTagAndText("td", "Gender"))
                .sibling(0)
                .shouldHave(text(gender));
        $("tbody").find(byTagAndText("td", "Mobile"))
                .sibling(0)
                .shouldHave(text(numberEntered));
        $("tbody").find(byTagAndText("td", "Date of Birth"))
                .sibling(0)
                .shouldHave(text(day), text(month), text(year));
        $("tbody").find(byTagAndText("td", "Subjects"))
                .sibling(0)
                .shouldHave(text(chosenSubject));
        $("tbody").find(byTagAndText("td", "Hobbies"))
                .sibling(0)
                .shouldHave(text(hobby1), text(hobby2), text(hobby3));
        $("tbody").find(byTagAndText("td", "Picture"))
                .sibling(0)
                .shouldHave(text(pictureName));
        $("tbody").find(byTagAndText("td", "Address"))
                .sibling(0)
                .shouldHave(text(currentAddress));
        $("tbody").find(byTagAndText("td", "State and City"))
                .sibling(0)
                .shouldHave(text(state), text(city));
        $("#closeLargeModal").click();
        $(".modal-content").shouldNot(exist);
    }
}
