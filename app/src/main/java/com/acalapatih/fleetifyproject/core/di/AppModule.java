package com.acalapatih.fleetifyproject.core.di;

import com.acalapatih.fleetifyproject.core.data.network.ApiService;
import com.acalapatih.fleetifyproject.core.repository.ListLaporanRepository;
import com.acalapatih.fleetifyproject.core.repository.PostLaporanRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    static String baseURL = "https://public-api-staging-dot-fair-catcher-256606.el.r.appspot.com/api/android/";

    @Singleton
    @Provides
    public static ApiService provideReportApi(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    public static Retrofit provideRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    public static PostLaporanRepository providePostLaporanRepository(ApiService apiService) {
        return new PostLaporanRepository(apiService);
    }

    @Singleton
    @Provides
    public static ListLaporanRepository provideListLaporanRepository(ApiService apiService) {
        return new ListLaporanRepository(apiService);
    }
}

