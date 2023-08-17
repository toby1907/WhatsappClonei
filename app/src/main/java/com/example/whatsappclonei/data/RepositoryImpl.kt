package com.example.whatsappclonei.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.whatsappclonei.Constants.TAG
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.data.model.User
import com.example.whatsappclonei.screens.LOGIN_SCREEN
import com.example.whatsappclonei.screens.MESSAGE_SCREEN
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val fireStorage: FirebaseStorage
) : AuthRepository {

    override val currentUser get() = auth.currentUser


    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String,username: String
    ): SignUpResponse {
        val user = hashMapOf(
            "email" to currentUser?.email,
            "username" to username,
            "userId" to currentUser?.uid,
        )
        return try {

            auth.createUserWithEmailAndPassword(email, password).await()
            val id: String? = currentUser?.providerId
           if (id != null) {
                firestore.collection("Users").add(user).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User added successfully!")
                    } else {
                        Log.w(TAG, "Error adding user", task.exception)
                    }
                }
            }
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String, openAndPopUp: (String, String) -> Unit
    ): SignInResponse = suspendCoroutine {continuation ->

       auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "signIn:success")
          continuation.resume(Response.Success(true))
                openAndPopUp.invoke(MESSAGE_SCREEN, LOGIN_SCREEN)
            }
            else{
                it.exception?.let { it1 ->
                    Log.d(TAG,"login Failed")
                    continuation.resume(Response.Failure(it1)) }
            }
        }

    }


    override suspend fun reloadFirebaseUser(): ReloadUserResponse {
        return try {
            auth.currentUser?.reload()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun signOut() = auth.signOut()

    override suspend fun revokeAccess(): RevokeAccessResponse {
        return try {
            auth.currentUser?.delete()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

    override suspend fun getAccounts(): AccountsResponse {
        val users = mutableStateListOf<User>()



        return try {

           firestore.collection("Users")
               .whereNotEqualTo("userId",currentUser?.uid)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val user = document.toObject(User::class.java)
                       if (user.userId != currentUser?.uid)
                       { users.add(user) }
                    }
                    Log.d("messagevmm", "$result")
                }.await()

            Log.d("messagevmmm", "$users")
            Response.Success(users)

        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getUser(userId: String): Flow<User> {
        return flow {
            val user: User
            try {
                val documents = firestore.collection("Users")
                    .whereEqualTo("userId", userId)
                    .limit(1)
                    .get()
                    .await() // Suspend until the task is complete
                val document = documents.first()
                user = document.toObject(User::class.java)
                Log.d(TAG, "${document.id} => ${user}")
                emit(user) // Emit the user as a flow value
            } catch (e: Exception) {
                Log.w(TAG, "Error getting documents: ", e)
                throw e // Rethrow the exception
            }
        }
    }
}

/* // Add the user account to the list if it is not the current user
            if (uid != currentUser?.uid) {
                userAccounts.add(userAccount)
            }*/