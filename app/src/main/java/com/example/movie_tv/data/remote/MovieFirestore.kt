package com.example.movie_tv.data.remote

import android.util.Log
import com.example.movie_tv.data.local.dao.MovieDao
import com.example.movie_tv.data.model.Movie
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieFirestore(private val mMovieDao: MovieDao) {
    private val fire = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()

    fun insert(movieMap : HashMap<String, Any>) : Boolean{
        var returnValue = false
        var user = mAuth.currentUser
        if(user != null){
            fire.collection("users")
                .document(user.email!!)
                .collection("movies")
                .document(movieMap["id"].toString())
                .set(movieMap)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        returnValue = true
                        Log.i("FIRESTORE", "${movieMap["id"]}")
                    }
                    else{
                        Log.i("FIRESTORE", "Could not insert movie.")
                    }
                }
        }
        return returnValue
    }

    fun update(movieMap : HashMap<String, Any>) : Boolean{
        var returnValue = false
        var user = mAuth.currentUser
        if(user != null){
            fire.collection("users")
                .document(user.email!!)
                .collection("movies")
                .document(movieMap["id"].toString())
                .update(movieMap)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        returnValue = true
                        Log.i("FIRESTORE", "Successfully updated movie.")
                    }
                    else{
                        Log.i("FIRESTORE", "Could not update movie. ${movieMap["id"]}")
                    }
                }
        }
        return returnValue
    }

    fun delete(movieMap : HashMap<String, Any>) : Boolean{
        var returnValue = false
        var user = mAuth.currentUser
        if(user != null){
            fire.collection("users")
                .document(user.email!!)
                .collection("movies")
                .document(movieMap["id"].toString())
                .delete()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        returnValue = true
                        Log.i("FIRESTORE", "Successfully deleted movie.")
                    }
                    else{
                        Log.i("FIRESTORE", "Could not delete movie.")
                    }
                }
        }
        return returnValue
    }

    fun syncMovies() : Boolean{
        var returnValue = false
        var user = mAuth.currentUser
        if(user != null){
            fire.collection("users")
                .document(user.email!!)
                .collection("movies")
                .get()
                .addOnSuccessListener {
                    try {
                        for(doc in it){
                            Log.i("DOC", "${doc.id} => ${doc.data}");

                            var movie = Movie(doc.data["id"] as Long,
                                doc.data["name"] as String,
                                doc.data["year"] as Long,
                                doc.data["url"] as String,
                                doc.data["rating"] as Long,
                                doc.data["wishList"] as Boolean,
                                doc.data["watching"] as Boolean,
                                doc.data["watched"] as Boolean
                            )
                            CoroutineScope(IO).launch {
                                mMovieDao.insert(movie)
                            }
                        }
                        returnValue = true
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
        }
        return returnValue
    }
}