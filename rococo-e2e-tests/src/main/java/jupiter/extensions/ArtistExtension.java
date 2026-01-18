package jupiter.extensions;


import data.entities.ArtistEntity;
import data.repo.ArtistRepositoryHibernate;
import io.qameta.allure.Step;
import jupiter.annotations.Artist;
import model.ArtistJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import utils.ImageUtil;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;


@ParametersAreNonnullByDefault
public class ArtistExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ArtistExtension.class);
    private final ArtistRepositoryHibernate artistRepository = new ArtistRepositoryHibernate();

    @Override
    @Step("Создать художника")
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Artist.class)
                .ifPresent(artistAnno -> {
                    final String name = "".equals(artistAnno.name()) ?
                            "Leonardo di ser Piero da Vinci" : artistAnno.name();
                    final String bio = "".equals(artistAnno.bio()) ?
                            "Italian polymath of the High Renaissance who was active as a painter, draughtsman," +
                                    " engineer, scientist, theorist, sculptor, and architect" : artistAnno.bio();
                    String photoBase64 = ImageUtil.convertImageToBase64("img/leonardo.png");
                    ArtistEntity artist = new ArtistEntity();
                    artist.setName(name);
                    artist.setBiography(bio);
                    artist.setPhoto(photoBase64.getBytes());

                    artistRepository.createArtist(artist);
                    setArtist(ArtistJson.fromEntity(artist));
                });
    }

    @Override
    public void afterEach(ExtensionContext context) {
        artistRepository.deleteArtist(Objects.requireNonNull(getArtist()).getName());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(ArtistJson.class);
    }

    @Override
    public ArtistJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getArtist();
    }

    @Nullable
    public static ArtistJson getArtist() {
        final ExtensionContext context = TestMethodContextExtension.context();
        return context.getStore(NAMESPACE).get(
                context.getUniqueId(), ArtistJson.class
        );
    }

    public static void setArtist(ArtistJson artist) {
        final ExtensionContext context = TestMethodContextExtension.context();
        context.getStore(NAMESPACE).put(
                context.getUniqueId(),
                artist
        );
    }
}
