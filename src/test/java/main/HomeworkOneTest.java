package main;

import com.google.inject.Inject;
import components.HeaderComponent;
import components.TrainingComponent;
import extencions.UIExtension;
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
            @Tag("Открыть страницу каталога курсов https://otus.ru/catalog/courses"),
            @Tag("Найти курс по имени (имя курса должно передаваться как данные в тесте)"),
            @Tag("Кликнуть по плитке курса и проверить, что открыта страница верного курса"),
            @Tag("Для поиска курса по имени обязательно необходимо использовать stream api.")
    })
    @Test
    public void coursePageVerification() {
        coursesPage.openPage();
        String randomCourseName = coursesPage.getRandomCourseName();
        coursesPage.clickOnCourseByName(randomCourseName);
        assertThat(coursePage.isSelectedCoursePageOpened(randomCourseName))
                .as("Check that course page for '%s' is opened" + randomCourseName)
                .isTrue();
    }

    @DisplayName("Сценарий 2")
    @Tags({
            @Tag("Открыть страницу каталога курсов https://otus.ru/catalog/courses"),
            @Tag("Найти курсы, которые стартуют раньше и позже всех. Если даты совпадают, то выбрать все курсы, у которых дата совпадает."),
            @Tag("Проверить, что на карточке самого раннего/позднего курсов отображается верное название курса и дата его начала"),
            @Tag("Для поиска таких курсов необходимо использовать stream api и reduce. Также для проверки данных на странице карточки курса необходимо использовать jsoup.")
    })
    @Test
    public void earliestAndLatestCoursesVerification() {
        coursesPage.openPage();
        List<WebElement> earliestCourses = coursesPage.getEarliestCourses();
        List<WebElement> latestCourses = coursesPage.getLatestCourses();

        earliestCourses.forEach(course ->
                softly.assertThat(coursesPage.isCourseModelInPage(course))
                        .as("Earliest course should be present in the page: " + course.getText())
                        .isTrue());
        latestCourses.forEach(course ->
                softly.assertThat(coursesPage.isCourseModelInPage(course))
                        .as("Latest course should be present in the page: " + course.getText())
                        .isTrue());
        softly.assertAll();
    }
    @DisplayName("Сценарий 3")
    @Tags({
            @Tag("Открыть главную страницу https://otus.ru"),
            @Tag("В заголовке страницы открыть меню «Обучение» и выбрать случайную категорию курсов"),
            @Tag("Проверить, что открыт каталог курсов верной категории")
    })
    @Test
    public void selectedCourseVerification() {
        mainPage.openPage();
        headerComponent.hoverOnTrainingField();
        String courseName = trainingComponent.clickOnRandomCourseAndReturnName();
        assertThat(coursesPage.isCourseSelected(coursesPage.getOpenedCourseByName(courseName)))
                .as("Course should be present in the page" + courseName)
                .isTrue();
    }
}
