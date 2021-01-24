package com.ilyko.giphy.features.main.search.presentation

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.ilyko.giphy.R
import com.ilyko.giphy.core.library.viewmodel.bindViewModel
import com.ilyko.giphy.core.library.viewmodel.kodeinViewModel
import com.ilyko.giphy.core.base.presentation.BaseFragment
import com.ilyko.giphy.core.base.presentation.ViewModelFactory
import com.ilyko.giphy.databinding.FragmentSearchBinding
import com.ilyko.giphy.core.library.live_data.observe
import com.ilyko.giphy.features.main.search.data.api.entity.GifObject
import com.ilyko.giphy.features.main.search.presentation.common.SearchAdapter
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    override val kodeinModule = Kodein.Module(this::class.java.simpleName) {
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

        bindViewModel<SearchViewModel>() with provider {
            SearchViewModel(
                searchInteractor = instance()
            )
        }
    }

    private val adapter = SearchAdapter()
    private val viewModel: SearchViewModel by kodeinViewModel()
    private lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view).apply {
            gifList.adapter = adapter
            search.doAfterTextChanged {
                viewModel.submitRequest(it.toString())
            }
        }
        observeEvents(viewModel.eventsQueue, ::onEvent)
        observe(viewModel.searchResult, ::updateUi)
    }

    private fun updateUi(searchResult: PagedList<GifObject>?) {
        adapter.submitList(searchResult)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
