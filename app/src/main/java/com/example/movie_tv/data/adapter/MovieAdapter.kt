package com.example.movie_tv.data.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_tv.R
import com.example.movie_tv.data.model.Movie
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class MovieAdapter(val context: Context, val listener: OnItemClickListener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val mInflater : LayoutInflater = LayoutInflater.from(context)
    private var mMovies : List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        var itemView:View = mInflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    private suspend fun loadImage(url:String, imageView: ImageView){
        var drawable:Drawable? = context.getDrawable(R.drawable.movieslist)

        try {
            val inputStream:InputStream = URL(url).content as InputStream
            drawable = Drawable.createFromStream(inputStream, "src")
        }catch (e:Exception){
            e.printStackTrace()
        }

        withContext(Main){
            try {
                imageView.setImageDrawable(drawable)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie: Movie = mMovies[position]
        val tmpRating:Long = currentMovie.movieRating

        //  Handling the UI
        holder.titleView.text = currentMovie.movieName
        holder.yearView.text = "Released : ${currentMovie.movieYear}"
        holder.rateindicator.rating= tmpRating.toFloat()

        CoroutineScope(IO).launch {
            loadImage(currentMovie.movieURL, holder.imageView)
        }

        holder.planButton.text = if(currentMovie.wishList){
            "Drop"
        }else{
            "Wish"
        }

        holder.watchingButton.isEnabled = if(currentMovie.watching){
            holder.watchedButton.isEnabled = true
            false
        }else{
            true
        }

        holder.watchedButton.isEnabled = if(currentMovie.watched){
            holder.watchingButton.isEnabled = true
            false
        }else{
            true
        }
    }

    fun setMovies(movies : List<Movie>){
        mMovies = movies
        notifyDataSetChanged()
    }

    //  View Holder along with click listener
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var titleView:TextView = itemView.findViewById(R.id.title_view)
        var yearView:TextView = itemView.findViewById(R.id.year_view)
        var imageView:ImageView = itemView.findViewById(R.id.image_view)
        var planButton: Button = itemView.findViewById(R.id.plan_button)
        var watchingButton: Button = itemView.findViewById(R.id.watching_button)
        var watchedButton: Button = itemView.findViewById(R.id.watched_button)
        var rateindicator : RatingBar = itemView.findViewById(R.id.indicatorbar)
        var deleteButton : ImageView = itemView.findViewById(R.id.delete_icon)

        init {
            planButton.setOnClickListener(this)
            watchingButton.setOnClickListener(this)
            watchedButton.setOnClickListener(this)
            deleteButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position, v)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int, view: View?)
    }
}