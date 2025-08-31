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

    @FindBy(xpath = "//main//section[2]//aф")
    private List<WebElement> courseLinks;

    @FindBy(xpath = "//main//section[2]//div[2]//a//h6/div")
    private List<WebElement> coursesNames;

    @FindBy(xpath = "//section[2]//a/div[2]/div/div")
    private List<WebElement> coursesDates;

    @FindBy(xpath = "//main//section[1]//div[1]//div[2]//div//div//div")
    private List<WebElement> coursesList;

    public CoursesPage(WebDriver driver) {
        super(driver);
    }

    // Случайный курс
    public String getRandomCourseName() {
        int index = (int) (Math.random() * coursesNames.size());
        return coursesNames.get(index).getText();
    }

    // Клик по курсу по имени
    public void clickOnCourseByName(String courseName) {
        // Нормализуем название для поиска
        String normalizedCourseName = courseName.trim().replaceAll("\\s+", " ").toLowerCase();

        for (int i = 0; i < coursesNames.size(); i++) {
            String currentName = coursesNames.get(i).getText().trim().replaceAll("\\s+", " ").toLowerCase();
            if (currentName.equals(normalizedCourseName)) {
                clickOnElement(courseLinks.get(i));
                return;
            }
        }

        throw new RuntimeException("Course not found: " + courseName);
    }

    // Общий метод для поиска курсов по дате
    private List<WebElement> getCoursesByDate(boolean earliest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));

        Optional<LocalDate> targetDateOpt = coursesDates.stream()
                .map(course -> {
                    // Отделяем только часть с датой
                    String text = course.getText().trim().split("·")[0].trim();
                    return LocalDate.parse(text, formatter);
                })
                .reduce((d1, d2) -> earliest ? (d1.isBefore(d2) ? d1 : d2) : (d1.isAfter(d2) ? d1 : d2));

        if (targetDateOpt.isEmpty()) {
            return List.of();
        }
        LocalDate targetDate = targetDateOpt.get();

        return coursesDates.stream()
                .filter(course -> {
                    String text = course.getText().trim().split("·")[0].trim();
                    return LocalDate.parse(text, formatter).equals(targetDate);
                })
                .collect(Collectors.toList());
    }

    public List<WebElement> getEarliestCourses() {
        return getCoursesByDate(true);
    }

    public List<WebElement> getLatestCourses() {
        return getCoursesByDate(false);
    }

    // Проверка, что курс есть на странице
    public boolean isCourseModelInPage(WebElement courseDate) {
        Document doc = Jsoup.parse(driver.getPageSource());
        return doc.selectFirst(String.format("div:contains(%s)", courseDate.getText().trim())) != null;
    }

    // Получение вебэлемента открытого курса по имени
    public WebElement getOpenedCourseByName(String courseName) {
        return coursesList.stream()
                .filter(course -> course.getText().equalsIgnoreCase(courseName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Opened course not found: " + courseName));
    }

    // Проверка, выбран ли курс
    public boolean isCourseSelected(WebElement webElement) {
        return "true".equals(getElementAttribute(webElement, "value"));
    }
}