package com.segunfamisa.zeitung.ui.newspreferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.segunfamisa.zeitung.App
import com.segunfamisa.zeitung.R
import javax.inject.Inject

class NewsPreferencesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NewsPreferencesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewsPreferencesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_preferences, container, false)
    }

    private fun injectDependencies() {
        (requireContext().applicationContext as App)
            .appComponent
            .with(preferencesModule = NewsPreferencesModule())
            .inject(this)
    }
}
