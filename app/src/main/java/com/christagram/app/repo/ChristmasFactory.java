package com.christagram.app.repo;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChristmasFactory {

  private final static String BASE_API_URL = "http://xmasmarket-finder.dev.zooplus.net/api/";

  public static MarketsRepo create() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    return retrofit.create(MarketsRepo.class);
  }
}
