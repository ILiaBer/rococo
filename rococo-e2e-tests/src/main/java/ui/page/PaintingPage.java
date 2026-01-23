package ui.page;

import org.openqa.selenium.By;
import ui.components.*;

public class PaintingPage {

    public final Table<PaintingPage> table = new Table<>(By.xpath("//ul[contains(@class,'grid')]"), this);
    public final Button<PaintingPage> addPicture = new Button<>(By.cssSelector("div button.variant-filled-primary"), this);
    public final LineEdit<PaintingPage> searchInput = new LineEdit<>(By.cssSelector("input[type=search]"), this);
    public final Button<PaintingPage> searchBtn = new Button<>(By.xpath("//img[@alt='Иконка поиска']//parent::button"), this);



    public static class AddPictureModal {

        public final LineEdit<AddPictureModal> title =
                new LineEdit<>(By.cssSelector("input[name=title]"), this);

        public final UploadFile<AddPictureModal> imageUpload =
                new UploadFile<>(By.cssSelector("input[name=content]"), this);

        public final Select<AddPictureModal> author =
                new Select<>(By.cssSelector("select[name=authorId]"), this);

        public final LineEdit<AddPictureModal> description =
                new LineEdit<>(By.cssSelector("textarea[name=description]"), this);

        public final Select<AddPictureModal> museumStorage =
                new Select<>(By.cssSelector("select[name=museumId]"), this);

        public final Button<AddPictureModal> submit =
                new Button<>(By.xpath("//button[contains(text(),'Добавить')]"), this);
    }

    public AddPictureModal addPictureModal() {
        return new AddPictureModal();
    }
}
