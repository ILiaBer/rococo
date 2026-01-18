package ui.page;

import org.openqa.selenium.By;
import ui.components.*;

public class MuseumPage {

    public final Table<MuseumPage> table = new Table<>(By.xpath("//ul[contains(@class,'grid')]"), this);
    public final Button<MuseumPage> addMuseum = new Button<>(By.cssSelector("div button.variant-filled-primary"), this);
    public final LineEdit<MuseumPage> searchInput = new LineEdit<>(By.cssSelector("input[type=search]"), this);
    public final Button<MuseumPage> searchBtn = new Button<>(By.xpath("//img[@alt='Иконка поиска']//parent::button"), this);


    public static class AddMuseumModal {

        public final LineEdit<AddMuseumModal> title =
                new LineEdit<>(By.cssSelector("input[name=title]"), this);

        public final Select<AddMuseumModal> country =
                new Select<>(By.cssSelector("select[name=countryId]"), this);

        public final LineEdit<AddMuseumModal> city =
                new LineEdit<>(By.cssSelector("input[name=city]"), this);

        public final UploadFile<AddMuseumModal> imageUpload =
                new UploadFile<>(By.cssSelector("input[name=photo]"), this);

        public final LineEdit<AddMuseumModal> description =
                new LineEdit<>(By.cssSelector("textarea[name=description]"), this);

        public final Button<AddMuseumModal> submit =
                new Button<>(By.xpath("//button[contains(text(),'Добавить')]"), this);
    }

    public AddMuseumModal addMuseumModal() {
        return new AddMuseumModal();
    }
}
