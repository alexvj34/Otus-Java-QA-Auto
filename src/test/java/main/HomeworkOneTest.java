package main;

import com.google.inject.Inject;
import components.HeaderComponent;
import components.TrainingComponent;
import extencions.UIExtension;
import org.assertj.core.api.SoftAssertions;
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

    @Test
    public void coursePageVerification() {
        coursesPage.openPage();
        String randomCourseName = coursesPage.getRandomCourseName();
        coursesPage.clickOnCourseByName(randomCourseName);
        assertThat(coursePage.isSelectedCoursePageOpened(randomCourseName))
                .as("Check that course page for '%s' is opened" + randomCourseName)
                .isTrue();
    }

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
