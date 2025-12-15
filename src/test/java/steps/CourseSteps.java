package steps;

import com.google.inject.Inject;
import components.HeaderComponent;
import components.TrainingComponent;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import pages.CoursePage;
import pages.CoursesPage;
import pages.CoursesPreparePage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CourseSteps {

  @Inject
  private CoursesPage coursesPage;
  @Inject
  private CoursePage coursePage;

  @Inject
  private HeaderComponent headerComponent;
  @Inject
  private TrainingComponent trainingComponent;

  @Inject
  private CoursesPreparePage coursesPreparePage; // <-- добавили подготовительные курсы
  private List<WebElement> filtered;

  @Дано("Я открываю браузер {string}")
  public void iOpenBrowser(String browser) {
    System.setProperty("browser", browser);
  }

  @И("Я открываю страницу Курсы")
  public void openCoursesPage() {
    //Hooks.injector.injectMembers(this);
    coursesPage.openPage();
  }

  @Когда("Я ищу курс с названием {string}")
  public void searchCourse(String name) {
    coursesPage.clickOnCourseByName(name);
  }

  @Тогда("Страница курса {string} должна быть открыта")
  public void verifyCourseOpened(String courseName) {
    Assertions.assertThat(coursePage.isSelectedCoursePageOpened(courseName))
        .isTrue();
  }

  @Когда("Я получаю курсы начиная с {string}")
  public void getCoursesFromDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDate targetDate = LocalDate.parse(date, formatter);

    filtered = coursesPage.coursesDates.stream()
        .filter(el -> {
          String elDate = el.getText().split("·")[0].trim();
          LocalDate parsed = LocalDate.parse(elDate,
              DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru")));
          return !parsed.isBefore(targetDate);
        })
        .toList();
  }

  @Тогда("Я вывожу эти курсы в консоль")
  public void printCourses() {
    filtered.forEach(e -> System.out.println("Курс: " + e.getText()));
  }

  @Когда("Я перехожу в Подготовительные курсы")
  public void openPrepCourses() {
    //Hooks.injector.injectMembers(this);
    headerComponent.hoverOnTrainingField();
    trainingComponent.openPrepCourses();
  }

  @Тогда("Я вывожу самый дорогой и самый дешевый курс")
  public void printMinMax() {

    // используем coursePrices из CoursesPreparePage
    List<WebElement> prices = coursesPreparePage.coursePrices;

    WebElement min = prices.stream()
        .min(Comparator.comparingInt(this::price))
        .orElse(null);
    WebElement max = prices.stream()
        .max(Comparator.comparingInt(this::price))
        .orElse(null);

    if (min != null) System.out.println("Самый дешевый: " + min.getText());
    if (max != null) System.out.println("Самый дорогой: " + max.getText());

    int minIndex = prices.indexOf(min);
    int maxIndex = prices.indexOf(max);

    System.out.println("Самый дешевый: " + coursesPreparePage.courseNames.get(minIndex).getText() + " — " + min.getText());
    System.out.println("Самый дорогой: " + coursesPreparePage.courseNames.get(maxIndex).getText() + " — " + max.getText());
  }

  private int price(WebElement el) {
    String text = el.getText().replaceAll("[^0-9]", "");
    return Integer.parseInt(text);
  }
}
