package api.impl;

import api.AuthApi;
import api.core.CodeInterceptor;
import api.core.RestClient;
import api.core.ThreadSafeCookieStore;
import com.fasterxml.jackson.databind.JsonNode;

import io.qameta.allure.Step;
import jupiter.extensions.ApiLoginExtension;
import lombok.SneakyThrows;
import retrofit2.Response;
import utils.auth.OAuthUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

@ParametersAreNonnullByDefault
public class AuthApiClient extends RestClient {

    private final AuthApi authApi;

    public AuthApiClient() {
        super(CFG.authUrl(), false, new CodeInterceptor());
        this.authApi = create(AuthApi.class);
    }

    @Step("Register user with username '{0}' using REST API")
    public void createUser(String username, String password) {
        try {
            authApi.requestRegisterForm().execute();
            authApi.register(
                    username,
                    password,
                    password,
                    ThreadSafeCookieStore.INSTANCE.cookieValue("XSRF-TOKEN")
            ).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public String login(String username, String password) {
        final String codeVerifier = OAuthUtils.generateCodeVerifier();
        final String codeChallenge = OAuthUtils.generateCodeChallenge(codeVerifier);
        final String redirectUri = CFG.frontUrl() + "authorized";
        final String clientId = "client";

        authApi.authorize(
                "code",
                clientId,
                "openid",
                redirectUri,
                codeChallenge,
                "S256"
        ).execute();

        authApi.login(
                username,
                password,
                ThreadSafeCookieStore.INSTANCE.cookieValue("XSRF-TOKEN")
        ).execute();

        String code = ApiLoginExtension.getCode();
        if (code == null) {
            throw new IllegalStateException("Authorization code is null");
        }

        Response<JsonNode> tokenResponse = authApi.token(
                ApiLoginExtension.getCode(),
                redirectUri,
                clientId,
                codeVerifier,
                "authorization_code"
        ).execute();

        return tokenResponse.body().get("id_token").asText();
    }
}