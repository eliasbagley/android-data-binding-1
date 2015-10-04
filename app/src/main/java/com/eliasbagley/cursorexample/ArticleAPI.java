package com.eliasbagley.cursorexample;

import com.eliasbagley.cursorexample.Network.ServiceResponse.ArticlesServiceResponse;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by eliasbagley on 9/29/15.
 */
public interface ArticleAPI {
    @GET("/")
    void getArticles(Callback<ArticlesServiceResponse> callback);
}
