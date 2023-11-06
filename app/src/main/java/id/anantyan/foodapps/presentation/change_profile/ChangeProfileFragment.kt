package id.anantyan.foodapps.presentation.change_profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.common.emailValid
import id.anantyan.foodapps.common.passwordValid
import id.anantyan.foodapps.common.usernameValid
import id.anantyan.foodapps.data.local.database.RoomDB
import id.anantyan.foodapps.data.local.repository.PreferencesRepositoryImpl
import id.anantyan.foodapps.data.local.repository.UserRepositoryImpl
import id.anantyan.foodapps.databinding.FragmentChangeProfileBinding
import id.anantyan.foodapps.di.ChangeProfileFactory
import id.anantyan.foodapps.domain.model.UserModel
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserUseCase
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChangeProfileFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentChangeProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChangeProfileViewModel by viewModels {
        ChangeProfileFactory(
            PreferencesUseCase(PreferencesRepositoryImpl(requireContext())),
            UserUseCase(UserRepositoryImpl(RoomDB.database(requireContext()).usersDao()))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.btnSave.setOnClickListener {
            onValidation()
        }
    }

    private fun onValidation() {
        validator(requireContext()) {
            mode = Mode.SINGLE
            listener = onValidationListener()
            validate(
                binding.txtInputUsername.usernameValid(),
                binding.txtInputEmail.emailValid(),
                binding.txtInputPassword.passwordValid()
            )
        }
    }

    private fun onValidationListener() = object : Validator.OnValidateListener {
        override fun onValidateFailed(errors: List<String>) { }

        override fun onValidateSuccess(values: List<String>) {
            val result = UserModel(
                id = viewModel.getUserId,
                username = binding.txtUsername.text.toString(),
                email = binding.txtEmail.text.toString(),
                password = binding.txtPassword.text.toString()
            )
            viewModel.changeProfile(result)
        }
    }

    private fun bindObserver() {
        viewModel.checkProfile()
        viewModel.checkProfile.onEach { state ->
            when (state) {
                is UIState.Loading -> {}
                is UIState.Success -> {
                    binding.txtUsername.setText(state.data?.username)
                    binding.txtEmail.setText(state.data?.email)
                }
                is UIState.Error -> {
                    Toast.makeText(requireContext(), state.message!!, Toast.LENGTH_LONG).show()
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)

        viewModel.changeProfile.onEach { state ->
            when (state) {
                is UIState.Loading -> {}
                is UIState.Success -> {
                    Toast.makeText(requireContext(), getString(state.data!!), Toast.LENGTH_LONG).show()
                }
                is UIState.Error -> {
                    Toast.makeText(requireContext(), getString(state.message!!), Toast.LENGTH_LONG).show()
                }
            }
        }.flowWithLifecycle(lifecycle).launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}