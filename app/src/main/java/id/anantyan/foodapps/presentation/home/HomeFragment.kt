package id.anantyan.foodapps.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.anantyan.foodapps.NavGraphMainDirections
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.common.calculateSpanCount
import id.anantyan.foodapps.data.local.repository.PreferencesRepositoryImpl
import id.anantyan.foodapps.data.remote.network.AppNetwork
import id.anantyan.foodapps.data.remote.repository.FoodsRepositoryImpl
import id.anantyan.foodapps.data.remote.service.FoodsApi
import id.anantyan.foodapps.databinding.FragmentHomeBinding
import id.anantyan.foodapps.di.HomeFactory
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        HomeFactory(FoodsUseCase(FoodsRepositoryImpl(AppNetwork.provideApi(requireContext()).create(FoodsApi::class.java))))
    }
    private val adapter: HomeAdapter by lazy { HomeAdapter() }
    private val adapterCategories: HomeCategoriesAdapter by lazy { HomeCategoriesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.layoutManager = StaggeredGridLayoutManager(requireActivity().windowManager.calculateSpanCount(), RecyclerView.VERTICAL)
        binding.rvHome.itemAnimator = DefaultItemAnimator()
        binding.rvHome.isNestedScrollingEnabled = true
        binding.rvHome.adapter = adapter
        
        binding.rvType.setHasFixedSize(true)
        binding.rvType.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvType.itemAnimator = DefaultItemAnimator()
        binding.rvType.isNestedScrollingEnabled = true
        binding.rvType.adapter = adapterCategories

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onClick { _, item ->
            val destination = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item.id ?: -1)
            findNavController().navigate(destination)
        }

        adapterCategories.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterCategories.onClick { _, item ->
            viewModel.results(item.key)
        }
    }

    private fun bindObserver() {
        viewModel.results()

        viewModel.resultsCategories.onEach { results ->
            adapterCategories.submitList(results)
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)

        viewModel.results.onEach { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.imgViewFavorite.isVisible = false
                    binding.rvHome.isVisible = false
                }
                is UIState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.imgViewFavorite.isVisible = false
                    binding.rvHome.isVisible = true
                    adapter.submitList(state.data)
                    if (state.data?.isEmpty() == true) {
                        binding.imgViewFavorite.isVisible = true
                        binding.rvHome.isVisible = false
                    }
                }
                is UIState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.imgViewFavorite.isVisible = true
                    binding.rvHome.isVisible = false
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}