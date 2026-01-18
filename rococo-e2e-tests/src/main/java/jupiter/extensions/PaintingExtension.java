package jupiter.extensions;


import data.entities.PaintingEntity;
import data.repo.PaintingRepositoryHibernate;
import io.qameta.allure.Step;
import jupiter.annotations.Artist;
import jupiter.annotations.Museum;
import jupiter.annotations.Painting;
import model.ArtistJson;
import model.MuseumJson;
import model.PaintingJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import utils.ImageUtil;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static utils.InputGenerators.randomPaintingDescription;
import static utils.InputGenerators.randomPaintingName;


@ParametersAreNonnullByDefault
public class PaintingExtension implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(PaintingExtension.class);
    private final PaintingRepositoryHibernate paintingRepository = new PaintingRepositoryHibernate();

    @Override
    @Step("Создать картину")
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Painting.class)
                .ifPresent(paintingAnno -> {

                    if (AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Museum.class).isEmpty()) {
                        throw new IllegalStateException("@Museum should be present in case of @Painting");
                    }

                    if (AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Artist.class).isEmpty()) {
                        throw new IllegalStateException("@Artist should be present in case of @Painting");
                    }

                    final String title = "".equals(paintingAnno.title()) ?
                            "La Gioconda" : paintingAnno.title();
                    final String description = "".equals(paintingAnno.description()) ?
                            "картина Леонардо да Винчи, одно из самых известных произведений живописи." +
                                    " Точная дата написания неизвестна." : paintingAnno.description();
                    final String path = "".equals(paintingAnno.path()) ?
                            "img/laGioconda.png" : paintingAnno.path();
                    final MuseumJson museum = MuseumExtension.getMuseum();
                    final ArtistJson artist = ArtistExtension.getArtist();
                    String photoBase64 = ImageUtil.convertImageToBase64(path);
                    PaintingEntity painting = new PaintingEntity();
                    painting.setTitle(title);
                    painting.setDescription(description);
                    painting.setContent(photoBase64.getBytes());
                    painting.setMuseumId(museum.getId());
                    painting.setArtistId(artist.getId());

                    paintingRepository.createPainting(painting);

                    context.getStore(NAMESPACE).put(context.getUniqueId(), PaintingJson.fromEntity(painting));
                });
    }

    @Nullable
    public static PaintingJson getPainting() {
        final ExtensionContext context = TestMethodContextExtension.context();
        return context.getStore(NAMESPACE).get(
                context.getUniqueId(), PaintingJson.class
        );
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(PaintingJson.class);
    }

    @Override
    public PaintingJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), PaintingJson.class);
    }
}
