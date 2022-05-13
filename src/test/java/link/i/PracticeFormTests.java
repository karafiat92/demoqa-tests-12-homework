package link.i;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PracticeFormTests {

//    String[] identArray = {
//            "firstName",
//            "lastName",
//            "userEmail",
//            "submit",
//            "gender-radio-1",
//            "gender-radio-2",
//            "gender-radio-3",
//            "userNumber",
//            "dateOfBirthInput",
//            "subjectsInput",
//            "hobbies-checkbox-1",
//            "hobbies-checkbox-2",
//            "hobbies-checkbox-3",
//            "uploadPicture",
//            "currentAddress",
//            "react-select-3-input",
//            "state",
//            "react-select-4-input"
//    };

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1200x1080";
    }

    @Test
    void fillFormsWithPositiveValues() {
        open("/automation-practice-form");
        $("#firstName").setValue("Ivan").shouldHave(value("Ivan"));
        $("#lastName").setValue("Ivanov").shouldHave(value("Ivanov"));
        $("#userEmail").setValue("Ivanov@mail.com").shouldHave(value("vanov@mail.com"));
        $("label[for=gender-radio-1]").click();
        $("input[id=gender-radio-1]").shouldBe(Condition.checked);
        $("label[for=gender-radio-2]").click();
        $("input[id=gender-radio-2]").shouldBe(Condition.checked);
        $("label[for=gender-radio-3]").click();
        $("input[id=gender-radio-3]").shouldBe(Condition.checked);
        $("#userNumber").setValue("1234567890123").shouldHave(value("1234567890"));
//        $("#dateOfBirthInput").setValue("01.01.2000");
//        $("#subjectsInput").setValue("Algebra");
//        $("#hobbies-checkbox-1").click();
//        $("#hobbies-checkbox-2").click();
//        $("#hobbies-checkbox-3").click();
        $("#uploadPicture").uploadFromClasspath("7777.png");
//        $("#currentAddress").setValue("Tushkina city, Kolotushkina street");
//        $("#state").click();
    }

}
