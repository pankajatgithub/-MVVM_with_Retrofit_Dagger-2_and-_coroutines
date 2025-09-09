package com.example.mvvm_with_retrofit_dagger_2_and__coroutines.di

import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.api.UserAPI
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class) //application level component defined in singletoncomponent
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofit: Retrofit):UserAPI{
        return retrofit.create(UserAPI::class.java)
    }

}