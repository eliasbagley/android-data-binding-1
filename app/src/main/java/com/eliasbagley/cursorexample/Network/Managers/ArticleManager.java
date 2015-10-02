package com.eliasbagley.cursorexample.Network.Managers;

import com.activeandroid.ActiveAndroid;
import com.eliasbagley.cursorexample.Models.Article;
import com.eliasbagley.cursorexample.Network.API.ArticleAPI;
import com.eliasbagley.cursorexample.Network.ServiceResponse.ArticlesServiceResponse;
import com.orm.SugarRecord;

import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by eliasbagley on 9/29/15.
 */
public class ArticleManager {

    private ArticleAPI _articleAPI;

    @Inject
    public ArticleManager(ArticleAPI articleAPI) {
        _articleAPI = articleAPI;
    }

    public void getArticles(final Callback<ArticlesServiceResponse> callback) {
        _articleAPI.getArticles(new Callback<ArticlesServiceResponse>() {
            @Override
            public void success(final ArticlesServiceResponse articlesServiceResponse, Response response) {

                ActiveAndroid.beginTransaction();
                try {
                    for (Article a : articlesServiceResponse.articles) {
                        a.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }

                if (callback != null) {
                    callback.success(articlesServiceResponse, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

}
