package com.yugandhar.apiapp.ui.main.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yugandhar.apiapp.databinding.FragmentUserDetailsBinding
import com.yugandhar.apiapp.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserDetailsFragment : Fragment() {
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val userId = arguments?.getInt("userId") ?: 0
//        val userId = UserDetailsFragmentArgs.fromBundle(requireArguments()).userId
        viewModel.fetchUser(userId)

        lifecycleScope.launch {
            viewModel.user.collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.detailsContainer.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.detailsContainer.visibility = View.VISIBLE
                        with(resource.data) {
                            binding.textName.text = name
                            binding.textUsername.text = username
                            binding.textEmail.text = email
                            binding.textPhone.text = phone
                            binding.textWebsite.text = website
                            binding.textAddress.text = "${address.street}, ${address.suite}, ${address.city}, ${address.zipcode}"
                            binding.textCompany.text = company.name
                        }
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}