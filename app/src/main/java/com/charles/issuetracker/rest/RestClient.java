package com.charles.issuetracker.rest;

/**
 * Created by charles on 2/19/17.
 */

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient {
    private static final String BASE_URL = "https://api.github.com/repos/rails/rails";

    public static IssueGateway getIssueGateway() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(SelfSigningClientBuilder.createClient()))
                .build();
        return restAdapter.create(IssueGateway.class);
    }
}

