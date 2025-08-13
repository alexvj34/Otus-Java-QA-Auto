package pages;

import annotations.Path;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Path("/")
public class CoursePage extends AbsBasePage {

    @FindBy(xpath = "//main//h1")
    WebElement courseTitle;
    //Возможная проблема — если на странице нет <h1> в <main>, тест упадёт при попытке взаимодействия.

    public CoursePage(WebDriver driver) {
        super(driver);
    }
    //Если страница ещё не была открыта — это может быть лишней операцией (мы это обсуждали ранее).

    public boolean isSelectedCoursePageOpened(String courseName) {
        return getText(courseTitle).contains(courseName);
    }
}