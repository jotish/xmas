package com.christagram.app.repo;

import com.christagram.app.data.Markets;
import com.christagram.app.data.venues.Explore;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MarketsRepo {

  @GET("xmasmarkets")
  Observable<Markets> fetchMarkets(@Query("location") String location);

  @GET("explore")
  Observable<Explore> fetchExplorations(@Query("ll") String location,
                                        @Query("radius") int radius);
}
