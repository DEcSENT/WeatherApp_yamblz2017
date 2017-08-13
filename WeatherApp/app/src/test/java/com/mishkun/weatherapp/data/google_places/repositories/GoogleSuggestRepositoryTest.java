package com.mishkun.weatherapp.data.google_places.repositories;

import android.util.Log;

import com.google.gson.Gson;
import com.mishkun.weatherapp.data.google_places.GooglePlacesApi;
import com.mishkun.weatherapp.data.google_places.detailCityInfo.LocationCity;
import com.mishkun.weatherapp.domain.entities.City;
import com.mishkun.weatherapp.domain.entities.Location;
import com.mishkun.weatherapp.data.google_places.citiesSuggest.CitiesSuggest;
import com.mishkun.weatherapp.data.google_places.citiesSuggest.Prediction;
import com.mishkun.weatherapp.data.google_places.detailCityInfo.DetailCityInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Created by DV on Space 5 
 * 27.07.2017
 */
@RunWith(MockitoJUnitRunner.class)
public class GoogleSuggestRepositoryTest {
    private GoogleSuggestRepository googleSuggestRepository;
    private CitiesSuggest citiesSuggest;
    private DetailCityInfo detailCityInfo;
    @Mock
    private GooglePlacesApi googlePlacesApi;
    private String citiesSuggestJSON = "{\"predictions\":[{\"description\":\"Paris, France\",\"id\":\"691b237b0322f28988f3ce03e321ff72a12167fd\",\"matched_substrings\":[{\"length\":5,\"offset\":0}],\"place_id\":\"ChIJD7fiBh9u5kcRYJSMaMOCCwQ\",\"reference\":\"CjQlAAAA0dMRKm09ZtfJJGNVvZ1Zk3tzbCjp3zHpW5fKPrkB4EKzisFyuCBrYmhOPLbulC7FEhA6uPhwXSGfIIpVCBhU4je1GhSm7fRj6OCwBfpXYtkCoHDs9cGDaQ\",\"structured_formatting\":{\"main_text\":\"Paris\",\"main_text_matched_substrings\":[{\"length\":5,\"offset\":0}],\"secondary_text\":\"France\"},\"terms\":[{\"offset\":0,\"value\":\"Paris\"},{\"offset\":7,\"value\":\"France\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Paris, Salles, France\",\"id\":\"cf0f3e7470e9c2cbe10cad936d31db0128d093a7\",\"matched_substrings\":[{\"length\":5,\"offset\":0}],\"place_id\":\"ChIJt0HoUJ7tVA0RLqe2NDiDIAQ\",\"reference\":\"CjQtAAAAN4FtGmnRIyasqPiG4HC_E8YTN3TXBKYCNXbiesiLne-cX8dUIliN3zZDuXb-YgFXEhDrl5kKTgHk4hogZxtPJEvlGhRUbnt2zc4JNgZRW7QuVKO-xBrJEw\",\"structured_formatting\":{\"main_text\":\"Paris\",\"main_text_matched_substrings\":[{\"length\":5,\"offset\":0}],\"secondary_text\":\"Salles, France\"},\"terms\":[{\"offset\":0,\"value\":\"Paris\"},{\"offset\":7,\"value\":\"Salles\"},{\"offset\":15,\"value\":\"France\"}],\"types\":[\"sublocality_level_1\",\"sublocality\",\"political\",\"geocode\"]},{\"description\":\"Paris, Seillons-Source-d'Argens, France\",\"id\":\"7e167ce616af80e3d39a21df0cb07dcc6959693c\",\"matched_substrings\":[{\"length\":5,\"offset\":0}],\"place_id\":\"EidQYXJpcywgU2VpbGxvbnMtU291cmNlLWQnQXJnZW5zLCBGcmFuY2U\",\"reference\":\"CjQrAAAAfMyy0b1xL0yTHiFZFVge5SXxoF19Ua1mK0CYVOdtQ9JyE-_5zds3v-A9ODfwJoPUEhBw-6MMtQqtkHcVTpPzp7cgGhSSLyzCK-XVxSu7XDyypSltf3QptA\",\"structured_formatting\":{\"main_text\":\"Paris\",\"main_text_matched_substrings\":[{\"length\":5,\"offset\":0}],\"secondary_text\":\"Seillons-Source-d'Argens, France\"},\"terms\":[{\"offset\":0,\"value\":\"Paris\"},{\"offset\":7,\"value\":\"Seillons-Source-d'Argens\"},{\"offset\":33,\"value\":\"France\"}],\"types\":[\"route\",\"geocode\"]},{\"description\":\"Pariser Platz, Berlin, Deutschland\",\"id\":\"0e85a595042c2602b2e86a4a9f4aaa4fe07d0bab\",\"matched_substrings\":[{\"length\":5,\"offset\":0}],\"place_id\":\"EiJQYXJpc2VyIFBsYXR6LCBCZXJsaW4sIERldXRzY2hsYW5k\",\"reference\":\"CjQmAAAAZNOblFncs7aPwkMSEBQo8icLLm8OfStj6vqR87kWmnZCKDrH91LN9-MQSXEAZAFGEhDwb2ZG1TdLAkCQJmfRx7UtGhTj5XJZlaewtU-GjoNDdu5nfmQcig\",\"structured_formatting\":{\"main_text\":\"Pariser Platz\",\"main_text_matched_substrings\":[{\"length\":5,\"offset\":0}],\"secondary_text\":\"Berlin, Deutschland\"},\"terms\":[{\"offset\":0,\"value\":\"Pariser Platz\"},{\"offset\":15,\"value\":\"Berlin\"},{\"offset\":23,\"value\":\"Deutschland\"}],\"types\":[\"route\",\"geocode\"]},{\"description\":\"Paris Gare de Lyon, Place Louis-Armand, Paris, France\",\"id\":\"bbc4781cfa22356b0a9f73de2206944076b4b4c5\",\"matched_substrings\":[{\"length\":5,\"offset\":0}],\"place_id\":\"ChIJS78AIBty5kcRZLsIMcKhPAA\",\"reference\":\"ClRCAAAAidrOi9POW7rrNmsYaOEnz8k07Mg2iQfYACkrZZZhDKcmkxnGVvcKq6waok2NHiGOBhEmEteXKkNFd0Yx2SUnPH27mWQoJ81nIfuJtyFl0LsSEImedkVG_uzFYgjtQVazeTgaFOY49WdQbV-Afq74e7BLiNjvpMds\",\"structured_formatting\":{\"main_text\":\"Paris Gare de Lyon\",\"main_text_matched_substrings\":[{\"length\":5,\"offset\":0}],\"secondary_text\":\"Place Louis-Armand, Paris, France\"},\"terms\":[{\"offset\":0,\"value\":\"Paris Gare de Lyon\"},{\"offset\":20,\"value\":\"Place Louis-Armand\"},{\"offset\":40,\"value\":\"Paris\"},{\"offset\":47,\"value\":\"France\"}],\"types\":[\"transit_station\",\"point_of_interest\",\"establishment\",\"geocode\"]}],\"status\":\"OK\"}";
    private String cityCoordinateJSON = "{\"html_attributions\":[],\"result\":{\"address_components\":[{\"long_name\":\"Москва\",\"short_name\":\"Москва\",\"types\":[\"locality\",\"political\"]},{\"long_name\":\"Москва\",\"short_name\":\"Москва\",\"types\":[\"administrative_area_level_2\",\"political\"]},{\"long_name\":\"Россия\",\"short_name\":\"RU\",\"types\":[\"country\",\"political\"]}],\"adr_address\":\"<span class=\\\"locality\\\">Москва</span>, <span class=\\\"country-name\\\">Россия</span>\",\"formatted_address\":\"Москва, Россия\",\"geometry\":{\"location\":{\"lat\":55.755826,\"lng\":37.6173},\"viewport\":{\"northeast\":{\"lat\":56.009657,\"lng\":37.9456611},\"southwest\":{\"lat\":55.48992699999999,\"lng\":37.3193289}}},\"icon\":\"https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png\",\"id\":\"1a0f08fcbc047354782f00ab52e66fb56d1aadf7\",\"name\":\"Москва\",\"photos\":[{\"height\":3869,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/103291604560917117840/photos\\\">鍾懷亞</a>\"],\"photo_reference\":\"CmRaAAAAk_kFRWVwwi8SOBilwpSIelqkv6DMlEIUtfzrn63Mij9JEXZeQci9tRu28GiDxYx7kjh1qKhr7dVhL0qROTUcwfKZjBqgYTG1yycCSdMr_MmtT5t5gYmg5EmTHNUnwmooEhAP33_fiWrfTFTqodUDgw9qGhTsGdcVr6mL_D2YIQ5LUw-fRbtLXg\",\"width\":5803},{\"height\":2160,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/104489255406387162989/photos\\\">Сергей Шмаков</a>\"],\"photo_reference\":\"CmRaAAAARjbnL3yD8JgWCVnwYvyZsmfmoJetxz29b5GFM8lRs7iDQsDgUqAzRknmPMekG2g8gTLRe8ZwVxvxpMH7213GqeR8w9utYIPajm0J8lmqBKay-LywMqjJhNZY_gjrXN-kEhCXYXpf7Twpxu1C88ca1lgtGhQlXwZFtmD1hs2OyGBfba2UkzlQ-w\",\"width\":3840},{\"height\":1333,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/107956383758290156645/photos\\\">Флотилия &quot;Рэдиссон Ройал, Москва&quot;</a>\"],\"photo_reference\":\"CmRaAAAAQb_56LBH_GDkJQ89bEAy9X-Yx3eT9Jk0PSWNPYipav7LQoeEUD-yYHpIiEMVScwZGXEyHZ-U2-WpTXhOnJtgQ7PJEE518VwwZIv-_qBxVp8_8S_KHnHHMPEiIqEXa80wEhALlurkYCy9wgpJijSZC0U_GhSrOQpPd_RDfC5tjA4mZFkl1a_uSw\",\"width\":2000},{\"height\":2160,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/114911699120710775872/photos\\\">PeriscopeLiveCorr</a>\"],\"photo_reference\":\"CmRaAAAA1h2XewPywlPJoYmXVczj4fZrnqfTWalqAZhck8NGgD1Aal5leJjiFv3Od1jw6GhLa0SY8AcD4OSJ9pwlfagzYVJpeQfRkteNV_W4vPQLy-uSzA1QDvLQDClVZk5Nr4UUEhBlOLnC_lF26XYFRi1W1Q7UGhRj5NlXvasQO-luF4yUupilJmXrPA\",\"width\":3840},{\"height\":2988,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/105260961063985950130/photos\\\">Ekaterina Rodkina</a>\"],\"photo_reference\":\"CmRaAAAAG5fU7XsmxSdzMjqvZFfEcFw-phUsA3CDbMPGFoncWnodeWkdRH2a64olgqJBxaVvDK3l5QyC47hvEVHGKTe5SMxQ4BgUWMZjh39kvqx-zTWfaBU5_T63_BDf5YpOEYQFEhAVF6O9rOm8cP6wVQPCnioPGhSiKP4hiAj4B2JQj1UacSu2Z6hyUw\",\"width\":5312},{\"height\":1836,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/117387643646577133592/photos\\\">Алла Цветкова</a>\"],\"photo_reference\":\"CmRaAAAAC_GeHKw7C_JaqaeuL24yOSdnb_TYJ9ZyTd1Vex6TUbx3POGWgtYRMPhQE2l9ndWU-BmmP5lKd62ZbMWJupj86GPIE3GYOgCviRTMwrXSh7M0-PbD3IKcAizAROr2SuhrEhCgOtzG3rvz-cqUHin2r7zNGhTJkU-LG3wjhOwT7YQPBGxxQh9sXg\",\"width\":3264},{\"height\":1536,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/109441349542816639937/photos\\\">Mack The Knife</a>\"],\"photo_reference\":\"CmRaAAAA-YAIv1l1N0au1i0b7mvYNECx_mgjIV2WTAZ8DpXePt98Lit7yH9IMKbvmxGOIB1moTrDp0_uZz444QZ4mxTNqylWHJwPamKLef2cLmkpR8j2Wm7SqEHbFRwVlCiIb9UqEhDJscAz_aunhwa829cM_NGaGhQKpeox_g5wWpzHIQNEIdhH91BOCg\",\"width\":2048},{\"height\":1333,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/107956383758290156645/photos\\\">Флотилия &quot;Рэдиссон Ройал, Москва&quot;</a>\"],\"photo_reference\":\"CmRaAAAATZlTEYlE_Lv1N50dZwdhy_cPME0D6tDsdD-ZRklxvOtbXvTpBLSw5IV7LoLJwZPH-3sKBnLuT8uoshpXcqei4CtY83BCZDR_b3rXrWDsLBOU9PNpq_yZVLKAvslZimy5EhB1BvK35feis1vTHCOejXZbGhTse0N1W3-jYuQz-QXsxeWzE__12g\",\"width\":2000},{\"height\":2824,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/110294063673222390548/photos\\\">pavel smirnov</a>\"],\"photo_reference\":\"CmRaAAAAoa4zrriT9bOOVsmHh3T2ZUfK542_ZQhZesOHJ9HaVk1j3P0vL-7Sm5bBof5a_4r3JRTNWYAAcIMv2BvuVfs0ordBNneG0ntE_cwFRJEwXQ-5PkZew4vm8BwS0-WxaivDEhBI9-F-r7jH2rMrli2pp1dfGhS8Yjiie53ooryKvFNgGUy5M6T0BQ\",\"width\":4048},{\"height\":1536,\"html_attributions\":[\"<a href=\\\"https://maps.google.com/maps/contrib/105110665095640414537/photos\\\">Michael M. St.</a>\"],\"photo_reference\":\"CmRaAAAAgJcMkidiiC8ghwaivY58MaQ_g2XcPM54Oe4V52Snk4MrG0jgHl-T0gH1QF2A2AcspNU41IiX6pE4q0Qdft6l0TFXeT-PTKiZ6hLvOHHIBVF8vDwa5HnKnDxGPw5NbR2oEhAz0T4PUOjGgz6B9g5eNOc5GhRqxXkGtm9oI1-DHYpvqmE_kl754Q\",\"width\":2048}],\"place_id\":\"ChIJybDUc_xKtUYRTM9XV8zWRD0\",\"reference\":\"CmRbAAAANLRZpYpztQXN5bWVdP1WdTGP684fOdIgKZojJOzry6XrLcWvl7szuh-WGySVphVTu_ewgcBdlWe7_BFYVjGPlCybBIhYwqBpAjyLNv1MkCEtbuEiy34P5GL5Rr5_6NSSEhDv7cCs0EAJ7ozqV8NItvDdGhRwIDAXCO6kT95yADVD6BVDoKMyWw\",\"scope\":\"GOOGLE\",\"types\":[\"locality\",\"political\"],\"url\":\"https://maps.google.com/?q=%D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0,+%D0%A0%D0%BE%D1%81%D1%81%D0%B8%D1%8F&ftid=0x46b54afc73d4b0c9:0x3d44d6cc5757cf4c\",\"utc_offset\":180,\"vicinity\":\"Москва\"},\"status\":\"OK\"}";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Gson jsonObject1 = new Gson();
        Gson jsonObject2 = new Gson();
        citiesSuggest = jsonObject1.fromJson(citiesSuggestJSON, CitiesSuggest.class);
        detailCityInfo = jsonObject2.fromJson(cityCoordinateJSON, DetailCityInfo.class);

        googleSuggestRepository = new GoogleSuggestRepository(googlePlacesApi);
    }

    @Test
    public void test_GetCitiesSuggest() throws Exception {
        when(googlePlacesApi.getSuggest(anyString(), anyString(), anyString()))
                .thenReturn(Single.just(citiesSuggest));

        Prediction testPrediction = new Prediction("Paris, France","691b237b0322f28988f3ce03e321ff72a12167fd",  "ChIJD7fiBh9u5kcRYJSMaMOCCwQ" );

        TestObserver<Prediction> listPredictionsTestObserver = googlePlacesApi.getSuggest(anyString(), anyString(), anyString())
                .map(citiesSuggest1 -> citiesSuggest1.getPredictions().get(0)).test();

        listPredictionsTestObserver.awaitTerminalEvent();
        listPredictionsTestObserver.assertNoErrors().assertValue(testPrediction);
    }

    @Test
    public void test_getCityCoordinates() throws Exception {
        when(googlePlacesApi.getDetailPlaceInfo(anyString(), anyString(), anyString()))
                .thenReturn(Single.just(detailCityInfo));

        City testCity = new City("Москва", new Location(55.755826, 37.6173));

        TestObserver<City> cityCoordinatesTestObserver = googlePlacesApi.getDetailPlaceInfo(anyString(), anyString(), anyString())
                .map((coords) -> {
                    LocationCity loc = coords.getResult().getGeometry().getLocationCity();
                    return new City(coords.getResult().getName(), new Location(loc.getLat(), loc.getLng()));
                }).test();

        cityCoordinatesTestObserver.awaitTerminalEvent();
        cityCoordinatesTestObserver.assertNoErrors().assertValue(testCity);
    }

//    @Test
//    public void getCitiesSuggest() throws Exception {
//        when(googlePlacesApi.getSuggest("paris", "(cities)", "test"))
//                .thenReturn(Single.just(citiesSuggest));
//        googleSuggestRepository.getCitiesSuggest("paris");
//        verify(googlePlacesApi).getSuggest("paris", "(cities)", "test");
//    }

    @Test
    public void getCityCoordinates() throws Exception {
        when(googlePlacesApi.getDetailPlaceInfo(anyString(), anyString(), anyString()))
                .thenReturn(Single.just(detailCityInfo));

        googleSuggestRepository.getCityCoordinates("test_id");
        verify(googlePlacesApi).getDetailPlaceInfo("test_id", "AIzaSyCnAOvg2liBhZVM72RQB8k201ehUYv4AMc", "ru_RU");
    }
}