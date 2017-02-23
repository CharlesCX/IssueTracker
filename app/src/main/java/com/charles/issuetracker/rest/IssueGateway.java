package com.charles.issuetracker.rest;

/**
 * Created by charles on 2/19/17.
 */

import com.charles.issuetracker.pojo.Comment;
import com.charles.issuetracker.pojo.Issues;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface IssueGateway {
    @GET("/issues")
    void getIssues(retrofit.Callback<List<Issues>> callback);

    @GET("/issues/{id}/comments")
    void getComment(@Path("id") String id, retrofit.Callback<List<Comment>> callback);
}
