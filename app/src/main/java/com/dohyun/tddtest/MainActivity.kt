package com.dohyun.tddtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dohyun.tddtest.data.model.User
import com.dohyun.tddtest.data.repository.UserRepository
import com.dohyun.tddtest.data.repository.UserRepositoryImpl
import com.dohyun.tddtest.databinding.ActivityMainBinding
import com.dohyun.tddtest.ui.UserState
import com.dohyun.tddtest.ui.UserViewModel
import com.dohyun.tddtest.ui.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var userRepository: UserRepository
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObject()
        bindView()
        observeData()
    }

    private fun initObject() {
        userRepository = UserRepositoryImpl(applicationContext)
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(userRepository = userRepository)
        )[UserViewModel::class.java]

        lifecycleScope.launch(Dispatchers.IO) {
            userRepository.insertUserData(user = User("cashwalk", "12345678910", "Tom", "Korea Seoul"))
        }
    }

    private fun bindView() {
        with(binding) {
            bClick.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    userViewModel.getUser(userId = "cashwalk", password = "12345678910")
                }
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.state.collect { state ->
                    binding.tvUserName.text = if (state is UserState.Success) {
                        state.user.name
                    } else {
                        "No User"
                    }
                }
            }
        }
    }
}