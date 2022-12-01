package com.example.onlineLib.utils;

public interface RestConstants {
    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "10";

    String AUTHENTICATION_HEADER = "Authorization";

    String[] OPEN_PAGES = {
            "/*",
            "/api/auth" + "/**",
            "/api/product/get/**",
            "/api/category/list",
            "/api/category/get/{id}"
    };
}
