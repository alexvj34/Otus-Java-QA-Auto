package pages;

import annotations.Path;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage {

    @FindBy(xpath = "//main//section[2]//a")
    List<WebElement> courseLinks;

    @FindBy(xpath = "//main//section[2]//div[2]//a//h6/div")////p[text()='Курс']//href::*
    List<WebElement> coursesNames;

    @FindBy(xpath = "//section[2]//a/div[2]/div/div")
    List<WebElement> coursesDates;

    @FindBy(xpath = "//main//section[1]//div[1]//div[2]//div//div//div")
    private List<WebElement> coursesList;

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    public String getRandomCourseName() {
        List<String> names = coursesNames.stream().map(WebElement::getText).toList();
        int index = (int) (Math.random() * coursesNames.size());
        return names.get(index);
        //Сейчас ты сначала создаёшь список names, а потом снова используешь coursesNames.size(). Это не критично, но лучше работать с одним объектом, чтобы избежать рассинхронизации.
        //public String getRandomCourseName() {
        //    int index = (int) (Math.random() * coursesNames.size());
        //    return coursesNames.get(index).getText();
        //}
    }

    public void clickOnCourseByName(String courseName) {
        WebElement webElement = courseLinks.stream()
                .filter(course -> course.getText().trim().contains(courseName)) // реализациа фильтра
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));
        clickOnElement(webElement);
        //Фильтрация по getText() может быть ненадёжной, если в тексте будут переносы строк или пробелы. Лучше нормализовать:
        //public void clickOnCourseByName(String courseName) {
        //    WebElement webElement = courseLinks.stream()
        //            .filter(course -> course.getText().trim().equalsIgnoreCase(courseName.trim()))
        //            .findFirst()
        //            .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));
        //    clickOnElement(webElement);
        //}
    }

    public List<WebElement> getEarliestCourses() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));
        Optional<LocalDate> earliestDateOpt = coursesDates.stream()
                .map(course -> {
                    String dateText = course.getText().trim().split(" . ")[0].trim();
                    return LocalDate.parse(dateText, formatter);
                })
                .reduce((d1, d2) -> d1.isBefore(d2) ? d1 : d2);
        if (earliestDateOpt.isEmpty()) {
            return List.of();
        }
        LocalDate earliestDate = earliestDateOpt.get();
        return coursesDates.stream()
                .filter(course -> {
                    String dateText = course.getText().trim().split(" . ")[0].trim();
                    return LocalDate.parse(dateText, formatter).equals(earliestDate);
                })
                .collect(Collectors.toList());
        //
        //private List<WebElement> getCoursesByDate(boolean earliest) {
        //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));
        //    Optional<LocalDate> targetDateOpt = coursesDates.stream()
        //            .map(course -> LocalDate.parse(course.getText().trim().split(" \\. ")[0].trim(), formatter))
        //            .reduce((d1, d2) -> earliest ? (d1.isBefore(d2) ? d1 : d2) : (d1.isAfter(d2) ? d1 : d2));
        //
        //    if (targetDateOpt.isEmpty()) {
        //        return List.of();
        //    }
        //    LocalDate targetDate = targetDateOpt.get();
        //
        //    return coursesDates.stream()
        //            .filter(course -> LocalDate.parse(course.getText().trim().split(" \\. ")[0].trim(), formatter)
        //                    .equals(targetDate))
        //            .collect(Collectors.toList());
        //}
        //
        //public List<WebElement> getEarliestCourses() {
        //    return getCoursesByDate(true);
        //}
        //
        //public List<WebElement> getLatestCourses() {
        //    return getCoursesByDate(false);
        //}
    }


    public List<WebElement> getLatestCourses() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));
        Optional<LocalDate> latestDates = coursesDates.stream()
                .map(course -> LocalDate.parse(course.getText().split(" . ")[0].trim(), formatter))
                .reduce(
                        (d1, d2) -> d1.isAfter(d2) ? d1 : d2); // необходимо использовать stream api и reduce.
        if (latestDates.isEmpty()) {
            return List.of();
        }
        LocalDate latestDate = latestDates.get();
        return coursesDates.stream()
                .filter(course -> LocalDate.parse(course.getText().split(" . ")[0].trim(), formatter).equals(latestDate))
                .collect(Collectors.toList());
    }

    public boolean isCourseModelInPage(WebElement courseDate) {
        Document doc = Jsoup.parse(driver.getPageSource());
        String courseDateText = courseDate.getText().trim();
        String query = String.format("div:contains(%s)", courseDateText);
        Element element = doc.select(query).first();
        return element != null;
        //Jsoup здесь используется для поиска по HTML, но это может быть лишним оверхедом, особенно при больших страницах. Если хочешь оставить, то хотя бы doc.selectFirst(query) != null:
        //public boolean isCourseModelInPage(WebElement courseDate) {
        //    return Jsoup.parse(driver.getPageSource())
        //            .selectFirst(String.format("div:contains(%s)", courseDate.getText().trim())) != null;
        //}
    }

    public WebElement getOpenedCourseByName(String courseName) {
        return coursesList.stream()
                .filter(course -> course.getText().equalsIgnoreCase(courseName))
                .findFirst()
                .get();
    }

    public boolean isCourseSelected(WebElement webElement) {
        if (getElementAttribute(webElement, "value").equals("true")) {
            return true;
        } else {
            return false;
        }
        //Метод можно сократить в одну строчку:
        //public boolean isCourseSelected(WebElement webElement) {
        //    return "true".equals(getElementAttribute(webElement, "value"));
        //}
    }
}