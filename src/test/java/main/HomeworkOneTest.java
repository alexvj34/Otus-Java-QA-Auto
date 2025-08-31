package main;

import com.google.inject.Inject;
import components.HeaderComponent;
import components.TrainingComponent;
import extensions.UIExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import pages.CoursePage;
import pages.CoursesPage;
import pages.MainPage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(UIExtension.class)
public class HomeworkOneTest {

    @Inject
    private CoursesPage coursesPage;

    SoftAssertions softly = new SoftAssertions();

    @Inject
    private CoursePage coursePage;

    @Inject
    private TrainingComponent trainingComponent;

    @Inject
    private HeaderComponent headerComponent;

    @Inject
    private MainPage mainPage;

    @DisplayName("Сценарий 1")
    @Tags({
            @Tag("Regress"),
            @Tag("UI"),
            @Tag("Smoke"),
            @Tag("Otus")
    })
    @Test
    public void coursePageVerification() {
        coursesPage.openPage();
        String randomCourseName = coursesPage.getRandomCourseName();
        coursesPage.clickOnCourseByName(randomCourseName);
        assertThat(coursePage.isSelectedCoursePageOpened(randomCourseName))
                .as("Проверка, страница курса '%s' открыта" + randomCourseName)
                .isTrue();
    }

    @DisplayName("Сценарий 2")
    @Tags({
            @Tag("Regress"),
            @Tag("Smoke"),
            @Tag("UI"),
            @Tag("Otus")
    })
    @Test
    public void earliestAndLatestCoursesVerification() {
        coursesPage.openPage();
        List<WebElement> earliestCourses = coursesPage.getEarliestCourses();
        List<WebElement> latestCourses = coursesPage.getLatestCourses();

        earliestCourses.forEach(course ->
                softly.assertThat(coursesPage.isCourseModelInPage(course))
                        .as("Самый ранний курс должен быть представлен на странице: " + course.getText())
                        .isTrue());
        latestCourses.forEach(course ->
                softly.assertThat(coursesPage.isCourseModelInPage(course))
                        .as("На странице должен быть представлен последний курс: " + course.getText())
                        .isTrue());
        softly.assertAll();
    }

    @DisplayName("Сценарий 3")
    @Tags({
            @Tag("Regress"),
            @Tag("Smoke"),
            @Tag("UI"),
            @Tag("Otus")
    })
    @Test
    public void selectedCourseVerification() {
        mainPage.openPage();
        headerComponent.hoverOnTrainingField();
        String courseName = trainingComponent.clickOnRandomCourseAndReturnName();
        assertThat(coursesPage.isCourseSelected(coursesPage.getOpenedCourseByName(courseName)))
                .as("Курс должен присутствовать на странице" + courseName)
                .isTrue();
    }
}