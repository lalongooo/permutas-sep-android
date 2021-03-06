package com.permutassep.data.repository.datasource.post;

/**
 * By Jorge E. Hernandez (@lalongooo) 2015
 */

import com.permutassep.data.entity.CityEntity;
import com.permutassep.data.entity.PostEntity;
import com.permutassep.data.entity.PostPageEntity;
import com.permutassep.data.entity.TownEntity;
import com.permutassep.data.repository.datasource.restful.inegifacil.InegiFacilRestClient;
import com.permutassep.data.repository.datasource.restful.permutassep.PermutasSEPRestClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * {@link PostDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudPostDataStore implements PostDataStore {

    private final PermutasSEPRestClient restClient;

    @Inject
    public CloudPostDataStore() {
        this.restClient = new PermutasSEPRestClient();
    }

    @Override
    public Observable<List<PostEntity>> getPostsList() {
        return restClient.get().getPosts();
    }

    @Override
    public Observable<PostEntity> getPostsDetails(int postId) {
        Observable<PostEntity> postEntityObservable = restClient.get().getPost(postId);

        return postEntityObservable.flatMap(postEntity -> {

            InegiFacilRestClient inegiFacilRestClient = new InegiFacilRestClient();

            Observable<List<CityEntity>> citiesFrom = inegiFacilRestClient.get().getCities(postEntity.getStateFrom());
            Observable<List<CityEntity>> citiesTo = inegiFacilRestClient.get().getCities(postEntity.getStateTo());
            Observable<List<TownEntity>> townsFrom = inegiFacilRestClient.get().getTowns(postEntity.getStateFrom(), postEntity.getCityFrom());
            Observable<List<TownEntity>> townsTo = inegiFacilRestClient.get().getTowns(postEntity.getStateTo(), postEntity.getCityTo());

            return Observable.zip(citiesFrom, citiesTo, townsFrom, townsTo, (cityEntitiesFrom, cityEntitiesTo, townEntitiesFrom, townEntitiesTo) -> {

                HashMap<Integer, CityEntity> mapCitiesFrom = new HashMap<>();
                for (CityEntity city : cityEntitiesFrom) {
                    mapCitiesFrom.put(city.getClaveMunicipio(), city);
                }

                HashMap<String, TownEntity> mapTownFrom = new HashMap<>();
                for (TownEntity town : townEntitiesFrom) {
                    mapTownFrom.put(town.getClave(), town);
                }

                HashMap<Integer, CityEntity> mapCitiesTo = new HashMap<>();
                for (CityEntity city : cityEntitiesTo) {
                    mapCitiesTo.put(city.getClaveMunicipio(), city);
                }

                HashMap<String, TownEntity> mapTownTo = new HashMap<>();
                for (TownEntity town : townEntitiesTo) {
                    mapTownTo.put(town.getClave(), town);
                }

                postEntity.setStateFromCode(postEntity.getStateFrom());
                postEntity.setStateToCode(postEntity.getStateTo());

                postEntity.setStateFrom(cityEntitiesFrom.get(0).getNombreEntidad());
                postEntity.setCityFrom(mapCitiesFrom.get(Integer.valueOf(postEntity.getCityFrom())).getNombreMunicipio());
                postEntity.setTownFrom(mapTownFrom.get(("0000".substring(0, "0000".length() - postEntity.getTownFrom().length())) + postEntity.getTownFrom()).getNombre());

                postEntity.setStateTo(cityEntitiesTo.get(0).getNombreEntidad());
                postEntity.setCityTo(mapCitiesTo.get(Integer.valueOf(postEntity.getCityTo())).getNombreMunicipio());
                postEntity.setTownTo(mapTownTo.get(("0000".substring(0, "0000".length() - postEntity.getTownTo().length())) + postEntity.getTownTo()).getNombre());

                return postEntity;
            });
        });
    }

    @Override
    public Observable<List<PostEntity>> userPosts(int userId) {
        return restClient.enablePostTypeAdapter().get().userPosts(userId);
    }

    @Override
    public Observable<List<PostEntity>> searchPosts(Map<String, String> parameters) {
        return restClient.get().searchPosts(parameters);
    }

    @Override
    public Observable<PostPageEntity> getPostPage(int page, int pageSize) {
        return restClient.get().getPostPage(page, pageSize);
    }

    @Override
    public Observable<PostEntity> newPost(PostEntity postEntity) {
        return restClient.enablePostTypeAdapter().get().newPost(postEntity);
    }
}
