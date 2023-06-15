package com.example.wasteless

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wasteless.data.FoodRepository
import com.example.wasteless.data.UserRepository
import com.example.wasteless.ui.screen.detail.DetailViewModel
import com.example.wasteless.ui.screen.explore.ExploreViewModel
import com.example.wasteless.ui.screen.home.HomeViewModel
import com.example.wasteless.ui.screen.profile.ProfileViewModel
import com.example.wasteless.ui.screen.viewmodel.UserViewModel

class ViewModelfactory(private val foodRepository: FoodRepository, private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel() as T
        }else if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel() as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel() as T
        }else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel() as T
        }else if (modelClass.isAssignableFrom(ExploreViewModel::class.java)){
            return ExploreViewModel() as T
        }
        throw java.lang.IllegalArgumentException("Unknown viewmodel class"+ modelClass.name)
    }
}