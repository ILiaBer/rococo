package web;

import config.Config;
import jupiter.annotations.User;
import jupiter.annotations.WebTest;
import model.UserJson;
import org.junit.jupiter.api.Test;
import ui.BaseUiTest;
import utils.BaseTest;

@WebTest
public class CheckTests extends BaseUiTest {
    private static final Config CFG = Config.getInstance();

    @Test
    @User
    void chekck(UserJson user) {
        System.out.println(CFG.frontUrl() + "!!!!!!!!!!!");
    }
}
