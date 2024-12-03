package com.example.buscadorpeliculas_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.buscadorpeliculas_app.Adapters.FilmAdapter
import com.example.buscadorpeliculas_app.databinding.ActivityMainBinding
import com.example.buscadorpeliculas_app.retrofit.FilmsDetails
import com.example.buscadorpeliculas_app.retrofit.FilmsResponse
import com.example.buscadorpeliculas_app.retrofit.SearchFilms
import com.example.buscadorpeliculas_app.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: FilmAdapter
    lateinit var searchView: SearchView
    var filmsList: List<FilmsDetails> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FilmAdapter(filmsList){position->navigateToDetail(filmsList[position])}

        binding.recyclerFilm.adapter = adapter
        binding.recyclerFilm.layoutManager = GridLayoutManager(this, 1)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchFilms("Batman")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity,menu)

        val menuItem=menu?.findItem(R.id.menuSearch)!!
        searchView= menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener
        {
            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null)
                {
                    searchFilms(query)
                    return true
                }
                return false
            }
        })

        return true
    }
    private fun searchFilms(query:String)
    {
        val service = RetrofitProvider.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            try
            {
                //Log.d("MainActivity",dataofHeros.listHero.toString())
                var result = service.searchFilm(query)
                CoroutineScope(Dispatchers.Main).launch {
                    if(result.response=="Error"||result.response=="False")
                    {
                        //TODO MOSTRAR MENSAJE DE QUE HAY QUE HACER ALGO
                    }else
                    {
                            filmsList =result.Search
                            adapter.setFilteredList(filmsList)

                    }
                }


            }
            catch (e: Exception) {
                Log.e("MainActivity",e.stackTraceToString())
            }

        }

    }
    fun navigateToDetail(filmSelected: FilmsDetails) {


        val intent: Intent = Intent(this, FilmDetailActivity::class.java)
        intent.putExtra("extra_ID",filmSelected.imdbID)
        startActivity(intent)

    }
}