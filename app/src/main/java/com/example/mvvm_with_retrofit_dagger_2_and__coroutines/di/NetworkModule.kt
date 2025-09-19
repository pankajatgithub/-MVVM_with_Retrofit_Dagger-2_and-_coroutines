package com.example.mvvm_with_retrofit_dagger_2_and__coroutines.di

import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.api.AuthInterceptor
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.api.NotesAPI
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.api.UserAPI
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class) //application level component defined in singletoncomponent
@Module
class NetworkModule {

//    @Singleton
//    @Provides
//    fun providesRetrofit():Retrofit{
//        return  Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//    }

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)

    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authTnterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addNetworkInterceptor(authTnterceptor).build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(providesRetrofitBuilder: Retrofit.Builder): UserAPI {
        return providesRetrofitBuilder.build().create(UserAPI::class.java)
    }

    // we need new retrofit oblject with token on header that would be used in all notes api

    //    @Singleton
//    @Provides
//    fun providesAuthRetrofit(okHttpClient: OkHttpClient):Retrofit{
//        return  Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .baseUrl(BASE_URL)
//            .build()
//    }
    @Singleton
    @Provides
    fun provideNotesAPI(providesRetrofitBuilder: Retrofit.Builder,okHttpClient: OkHttpClient): NotesAPI {
        return providesRetrofitBuilder
            .client(okHttpClient)
            .build().create(NotesAPI::class.java)
    }

}