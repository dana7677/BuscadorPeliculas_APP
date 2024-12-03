package com.example.buscadorpeliculas_app.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.buscadorpeliculas_app.databinding.ViewFilmItemBinding
import com.example.buscadorpeliculas_app.retrofit.FilmsDetails
import com.example.buscadorpeliculas_app.retrofit.FilmsResponse
import com.example.buscadorpeliculas_app.retrofit.SearchFilms
import com.squareup.picasso.Picasso


class FilmAdapter (private var filmList:List<FilmsDetails>, private val onItemClickListener: (Int) -> Unit):
    RecyclerView.Adapter<ViewHolder>()
    {
        //SearchViewConfig

        fun setFilteredList(filterList: List<FilmsDetails>) {
            this.filmList = filterList
            notifyDataSetChanged()

        }

        //Componenente dentro del ViewHolder que va a componer la vista


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            //De Context coge a la vista de su padre que lo ha creado.


            val binding =
                ViewFilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)

        }

        //Devolver automaticamente la cantidad de simbolos en nuestro Lista del zodiaco
        override fun getItemCount() = filmList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.bind(filmList[position],position)

            holder.itemView.setOnClickListener {
                onItemClickListener(position)
            }

        }

    }
class ViewHolder(private val binding: ViewFilmItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(filmData: FilmsDetails,pos:Int) {

        //val context = itemView.context
        binding.filmName.setText(filmData.title)
        binding.filmDate.setText(filmData.year)
        Picasso.get().load(filmData.poster).into(binding.imgFilm)

    }
}
