package com.christagram.app.repo;

import com.christagram.app.data.Markets;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MarketsRepo {

  @GET("xmasmarkets")
  Observable<Markets> fetchMarkets(@Query("location") String location);
}
