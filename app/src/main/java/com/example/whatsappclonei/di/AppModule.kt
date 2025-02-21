package com.example.whatsappclonei.di

import android.app.Application
import android.content.Context
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.data.AuthRepositoryImpl
import com.example.whatsappclonei.data.StatusRepository
import com.example.whatsappclonei.data.StatusRepositoryImpl
import com.example.whatsappclonei.ui.onboarding.validation.JsonParser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
@Provides
fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(auth = Firebase.auth, firestore =Firebase.firestore, fireStorage = FirebaseStorage.getInstance())

    @Provides
    fun provideStatusRepository(): StatusRepository = StatusRepositoryImpl(
        firestore = Firebase.firestore,
        auth = Firebase.auth
    )
    @Provides
    fun provideJsonParser(context: Context): JsonParser {
        return JsonParser(context)
    }
}

