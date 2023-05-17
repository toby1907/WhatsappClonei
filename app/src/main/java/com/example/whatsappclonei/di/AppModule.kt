package com.example.whatsappclonei.di

import android.app.Application
import android.content.Context
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.data.AuthRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
@Provides
fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(auth = Firebase.auth, firebaseDatabase = Firebase.database)
}