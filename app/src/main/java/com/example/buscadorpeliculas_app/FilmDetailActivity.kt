package com.example.buscadorpeliculas_app

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buscadorpeliculas_app.databinding.ActivityFilmDetailBinding
import com.example.buscadorpeliculas_app.databinding.ActivityMainBinding
import com.example.buscadorpeliculas_app.retrofit.FilmsDetails
import com.example.buscadorpeliculas_app.retrofit.FilmsResponse
import com.example.buscadorpeliculas_app.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityFilmDetailBinding
    lateinit var filmsResponse: FilmsResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultId = intent.extras?.getString("extra_ID")
        if(resultId!=null) {
            var resultPass=resultId.toString()
            filmDetails(resultPass)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            android.R.id.home->
            {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun filmDetails(query:String)
    {
        val service = RetrofitProvider.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            try
            {

                var result = service.getFilmList(query)
                CoroutineScope(Dispatchers.Main).launch {
                    if(result.response=="Error"||result.response=="False")
                    {
                        //TODO MOSTRAR MENSAJE DE QUE HAY QUE HACER ALGO
                    }else
                    {
                        filmsResponse =result
                        fillInfo()
                    }
                }


            }
            catch (e: Exception) {
                Log.e("MainActivity",e.stackTraceToString())
            }

        }

    }
    private fun fillInfo()
    {

        binding.dateFilm.setText(filmsResponse.year)
        binding.titleFilm.setText(filmsResponse.title)
        binding.sinopsis.setText(filmsResponse.plot)

        binding.duration.setText(filmsResponse.runtime)
        binding.director.setText(filmsResponse.director)
        binding.gener.setText(filmsResponse.genre)
        binding.country.setText(filmsResponse.country)

        Picasso.get().load(filmsResponse.poster).into(binding.imgPoster)

    }
}