package com.github.dsmiles.constants;

public final class ApiConstants {
    public static final String BASE_URI = "https://dev.portal.gpas.world";
    public static final String BASE_PATH = "/api/v1";

    public static final String BASE_URL = BASE_URI + BASE_PATH;
    public static final String AUTH_URL = BASE_PATH + "/auth/token";

    public static final String ADMIN_URL = BASE_PATH + "/admin";
    public static final String ORGANISATION_URL = ADMIN_URL + "/organisation";
    public static final String CREATE_USER_URL = ADMIN_URL + "/create-user";

    public static final String BATCHES_URL = BASE_URI + BASE_PATH + "/batches";

    public static final String CLI_VERSION_URL = BASE_URI + "/cli-version";

}
