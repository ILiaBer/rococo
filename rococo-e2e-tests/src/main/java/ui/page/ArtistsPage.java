package ui.page;

import org.openqa.selenium.By;
import ui.components.*;

public class ArtistsPage {

    public final Table<ArtistsPage> table = new Table<>(By.xpath("//ul[contains(@class,'grid')]"), this);
    public final Button<ArtistsPage> addArtist = new Button<>(By.cssSelector("div button.variant-filled-primary"), this);
    public final LineEdit<ArtistsPage> searchInput = new LineEdit<>(By.cssSelector("input[type=search]"), this);
    public final Button<ArtistsPage> searchBtn = new Button<>(By.xpath("//img[@alt='Иконка поиска']//parent::button"), this);

    public static class AddArtistModal {

        public final LineEdit<AddArtistModal> name =
                new LineEdit<>(By.cssSelector("input[name=name]"), this);

        public final UploadFile<AddArtistModal> imageUpload =
                new UploadFile<>(By.cssSelector("input[name=photo]"), this);

        public final LineEdit<AddArtistModal> biography =
                new LineEdit<>(By.cssSelector("textarea[name=biography]"), this);

        public final Button<AddArtistModal> submit =
                new Button<>(By.xpath("//button[contains(text(),'Добавить')]"), this);
    }

    public AddArtistModal addArtistModal() {
        return new AddArtistModal();
    }

    public static class PreviewArtistModal {

        public final Table<PreviewArtistModal> table = new Table<>(By.xpath("//ul[contains(@class,'grid')]"), this);

    }

    public PreviewArtistModal previewArtistModal() {
        return new PreviewArtistModal();
    }
}
