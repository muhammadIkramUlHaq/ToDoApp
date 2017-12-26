package com.github.todo;

public final class RestPath
{
    private RestPath()
    {
    }

    public final static String API = "/api";
    public final static String VERSION_1 = "/v1";

    public final static String API_VERSION_1 = API + VERSION_1;

    public final static String TODO = "/todo";
    public final static String API_VERSION_1_TODO = API_VERSION_1 + TODO;
}