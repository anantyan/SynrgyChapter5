package id.anantyan.foodapps.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.ViewSizeResolver
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.network.AppNetwork
import id.anantyan.foodapps.data.remote.repository.FoodsRepositoryImpl
import id.anantyan.foodapps.data.remote.service.FoodsApi
import id.anantyan.foodapps.databinding.FragmentDetailBinding
import id.anantyan.foodapps.di.DetailFactory
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels {
        DetailFactory(FoodsUseCase(FoodsRepositoryImpl(AppNetwork.provideApi(requireContext()).create(FoodsApi::class.java))))
    }
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_keyboard_backspace)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun bindObserver() {
        viewModel.result(args.idRecipe).onEach { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.contentDetail.isVisible = false
                    binding.imgViewFavorite.isVisible = false
                }
                is UIState.Success -> {
                    binding.progressBar.isVisible = false
                    binding.contentDetail.isVisible = true
                    binding.imgViewFavorite.isVisible = false
                    binding.toolbar.title = state.data?.title
                    binding.imgDetail.load(state.data?.image) {
                        crossfade(true)
                        placeholder(R.drawable.img_loading)
                        error(R.drawable.img_not_found)
                        size(ViewSizeResolver(binding.imgDetail))
                    }
                    Log.d("DEBUGGG", state.data?.image.toString())
                    @Suppress("SetTextI18n")
                    binding.txtSourceName.text = "by " + state.data?.sourceName
                    @Suppress("SetTextI18n")
                    binding.txtServings.text = state.data?.servings.toString() + " SERVINGS"
                    @Suppress("SetTextI18n")
                    binding.txtPrepareOnMinutes.text = state.data?.preparationMinutes.toString() + " MINS"
                    binding.txtSummary.text = Html.fromHtml(state.data?.summary, Html.FROM_HTML_MODE_COMPACT)
                }
                is UIState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.contentDetail.isVisible = false
                    binding.imgViewFavorite.isVisible = true
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}