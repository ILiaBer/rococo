package web;

import jupiter.annotations.*;
import model.UserJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebTest
public class PaintingTests extends BaseUiTest{

    @Test
    @DisplayName("WEB: Редактирование заголовка и описания картины")
//    @ApiLogin(user = @User())
    @Museum
    @User
    @Artist
    @Painting
    void check(UserJson user) {
        commonSteps().login(user);
    }
}
