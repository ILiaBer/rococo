package jupiter.extensions;


import data.entities.MuseumEntity;
import data.enums.Countries;
import data.repo.GeoRepositoryHibernate;
import data.repo.MuseumRepositoryHibernate;
import io.qameta.allure.Step;
import jupiter.annotations.Museum;
import model.MuseumJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;
import utils.ImageUtil;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.UUID;

import static utils.InputGenerators.randomMuseumDescription;
import static utils.InputGenerators.randomMuseumTitle;


@ParametersAreNonnullByDefault
public class MuseumExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(MuseumExtension.class);
    private final MuseumRepositoryHibernate museumRepository = new MuseumRepositoryHibernate();
    private final GeoRepositoryHibernate geoRepository = new GeoRepositoryHibernate();

    @Override
    @Step("Создать музей")
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Museum.class)
                .ifPresent(museumAnno -> {
                    final String title = "".equals(museumAnno.title()) ?
                            "Musée du Louvre" : museumAnno.title();
                    final String description = "".equals(museumAnno.description()) ?
                            "один из крупнейших и самый популярный художественный музей мира." +
                                    " Музей расположен в центре Парижа, на правом берегу Сены" : museumAnno.description();
                    final String photoPath = "".equals(museumAnno.path()) ?
                            "img/duLouvre.png" : museumAnno.path();
                    final String city = "".equals(museumAnno.city()) ?
                            "Paris" : museumAnno.city();
                    final UUID countryId = Countries.FRANCE.equals(museumAnno.country()) ?
                            geoRepository.getCountryByName(Countries.FRANCE.getName()).getId()
                            : geoRepository.getCountryByName(museumAnno.country().getName()).getId();
                    String photoBase64 = ImageUtil.convertImageToBase64(photoPath);
                    MuseumEntity museum = new MuseumEntity();
                    museum.setTitle(title);
                    museum.setDescription(description);
                    museum.setCity(city);
                    museum.setPhoto(photoBase64.getBytes());
                    museum.setGeoId(countryId);

                    MuseumEntity museumForTest = museumRepository.createMuseum(museum);
                    museum.setId(museumForTest.getId());
                    setMuseum(MuseumJson.fromEntity(museum));
                });
    }

    @Override
    public void afterEach(ExtensionContext context) {
        museumRepository.deleteMuseum(Objects.requireNonNull(getMuseum()).getTitle());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(MuseumJson.class);
    }

    @Override
    public MuseumJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return getMuseum();
    }

    public static void setMuseum(MuseumJson museum) {
        ExtensionContext context = TestMethodContextExtension.context();
        context.getStore(NAMESPACE).put(
                context.getUniqueId(),
                museum
        );
    }

    @Nullable
    public static MuseumJson getMuseum() {
        ExtensionContext context = TestMethodContextExtension.context();
        return context.getStore(NAMESPACE).get(
                context.getUniqueId(), MuseumJson.class
        );
    }
}
