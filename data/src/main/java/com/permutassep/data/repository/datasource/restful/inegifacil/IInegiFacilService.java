package com.permutassep.data.repository.datasource.restful.inegifacil;


import com.permutassep.data.entity.CityEntity;
import com.permutassep.data.entity.TownEntity;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface IInegiFacilService {

    @GET("/cities/{id}")
    Observable<List<CityEntity>> getCities(@Path("id") String stateId);

    @GET("/towns/{stateId}/{cityId}")
    Observable<List<TownEntity>> getTowns(@Path("stateId") String stateId, @Path("cityId") String cityId);
}