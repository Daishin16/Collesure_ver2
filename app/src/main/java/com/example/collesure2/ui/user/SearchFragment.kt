package com.example.collesure2.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.collesure2.R
import com.example.collesure2.data.ImageItem
import com.example.collesure2.data.network.EngineGoogle
import com.example.collesure2.data.network.SearchEngine
import com.example.collesure2.ui.list.RecyclerFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    var word = ""
    var nsfw = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)



        searchButton.setOnClickListener {
            word = searchWord.text.toString()
            nsfw = switch_nsfw.isChecked
            
            GlobalScope.launch(Dispatchers.Main) {
                async(Dispatchers.Default){
                    getMoreImageList(EngineGoogle(), word, nsfw)
                }.await().let {
                    showResultFragment(it)
                }
            }
        }
    }

    fun showResultFragment(imageList:ArrayList<ImageItem>){
        val recyclerFragment = RecyclerFragment()
        val fragmentManager = fragmentManager
        val bundle = Bundle()
        bundle.putSerializable("imageList", imageList)
        recyclerFragment.arguments = bundle
        val transaction = fragmentManager!!.beginTransaction().apply {
            replace(R.id.fragment, recyclerFragment)
            addToBackStack(null)
        }
        transaction.commit()
    }

    suspend fun getMoreImageList(engine:SearchEngine, word:String, nsfw:Boolean):ArrayList<ImageItem> {
        val imageList = arrayListOf<ImageItem>()
        for (page in 0..2) {
            imageList.addAll(engine.searchImage(word, page, nsfw))
        }
        return imageList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}