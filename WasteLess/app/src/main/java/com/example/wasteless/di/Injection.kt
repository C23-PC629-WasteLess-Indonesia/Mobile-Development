package com.example.wasteless.di

import com.example.wasteless.data.FoodRepository
import com.example.wasteless.data.UserRepository

object Injection {
    fun provideFoodRepository(): FoodRepository{
        return FoodRepository.getInstance()
    }

    fun provideUserRepository(): UserRepository{
        return UserRepository.getInstance()
    }
}