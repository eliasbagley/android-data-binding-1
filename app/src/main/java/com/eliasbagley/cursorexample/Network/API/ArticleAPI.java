package com.eliasbagley.cursorexample.Network.API;

import com.eliasbagley.cursorexample.Network.ServiceResponse.ArticlesServiceResponse;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by eliasbagley on 9/29/15.
 */
public interface ArticleAPI {
    @GET("articles.json")
    void getArticles(Callback<ArticlesServiceResponse> callback);
}
