package com.example.movie_tv.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_tv.BuildConfig
import com.example.movie_tv.R
import com.example.movie_tv.data.ApiTMDB
import com.example.movie_tv.data.adapter.MovieAdapter
import com.example.movie_tv.data.model.ApiResult
import com.example.movie_tv.data.model.Movie
import com.example.movie_tv.data.model.MovieJson
import com.example.movie_tv.data.viewmodel.MovieViewModel
import com.google.android.material.textfield.TextInputEditText
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.Exception
import kotlin.math.roundToLong

class FragmentsAdd : Fragment(), MovieAdapter.OnItemClickListener {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private var movies: ArrayList<Movie> = ArrayList()

    private lateinit var searchEditText: TextInputEditText
    private lateinit var searchButton: ImageButton

    private lateinit var retrofit:Retrofit
    private lateinit var api:ApiTMDB
    private lateinit var call:Call<ApiResult>
    private var moviesJson:List<MovieJson>? = ArrayList()


    private fun setupCallToApi(query:String){
        movies.clear()

        call = api.getResultFromApi(BuildConfig.API_KEY, query)

        call.enqueue(object : Callback<ApiResult> {
            override fun onResponse(
                call: Call<ApiResult>,
                response: Response<ApiResult>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(context, "${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.i("API", response.code().toString())
                    return
                }
                var apiResponse = response.body()
                moviesJson = apiResponse?.results

                if (moviesJson.isNullOrEmpty()) {
                    Toast.makeText(context, "No results found!", Toast.LENGTH_SHORT).show()
                }
                else{
                    for (movie in moviesJson!!) {
                        if (!movieViewModel.findMovies(movie.id.toLong())) {
                            try {
                                val year: Long = movie.release_date.substring(0, 4).toLong()

                                var image = ""
                                if(!movie.poster_path.isNullOrEmpty()){
                                    image = "https://image.tmdb.org/t/p/w500" + movie.poster_path
                                }

                                val rating: Long = ((movie.vote_average).toFloat() / 2).roundToLong()

                                val movieModel = Movie(
                                    movie.id.toLong(),
                                    movie.title,
                                    year,
                                    image,
                                    rating,
                                    false,
                                    false,
                                    false
                                )
                                movies.add(movieModel)
                            }
                            catch (e:Exception){
                                e.printStackTrace()
                            }
                        }
                    }

                    movieAdapter.setMovies(movies)
                }
            }

            override fun onFailure(call: Call<ApiResult>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                Log.i("API", t.message.toString())
            }

        })
    }

    fun onSearch(){
        val searchString = searchEditText.text
        if(searchString?.isNotEmpty()!!){
            setupCallToApi(searchString.toString())
        }else{
            Toast.makeText(context, "Invalid search!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        recyclerView = view.findViewById(R.id.recycler_view_search)
        movieAdapter = MovieAdapter(requireContext(), false, this)
        movieViewModel = MovieViewModel(activity?.application!!)

        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        searchEditText = view.findViewById(R.id.search_edit_text)
        searchButton = view.findViewById(R.id.search_movie_button)

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiTMDB::class.java)

        searchButton.setOnClickListener {
            onSearch()
        }

        return view
    }

    override fun onItemClick(position: Int, view: View?) {
        if(view?.tag == "wish"){
            var movie: Movie = movies[position]
            movie.wishList = true
            movieViewModel.insert(movie)
            movies.removeAt(position)
            movieAdapter.setMovies(movies)
        }
    }
}