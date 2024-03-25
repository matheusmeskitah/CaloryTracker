package com.meskitah.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.meskitah.tracker_data.local.TrackerDatabase
import com.meskitah.tracker_data.remote.OpenFoodApi
import com.meskitah.tracker_data.remote.OpenFoodApi.Companion.BASE_URL
import com.meskitah.tracker_data.repository.TrackerRepositoryImpl
import com.meskitah.tracker_domain.repository.TrackerRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodApi(
        client: OkHttpClient,
        moshi: Moshi
    ): OpenFoodApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): TrackerDatabase {
        return Room.databaseBuilder(app, TrackerDatabase::class.java, "tracker_db").build()
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(
        api: OpenFoodApi,
        db: TrackerDatabase
    ): TrackerRepository {
        return TrackerRepositoryImpl(dao = db.dao, api = api)
    }
}