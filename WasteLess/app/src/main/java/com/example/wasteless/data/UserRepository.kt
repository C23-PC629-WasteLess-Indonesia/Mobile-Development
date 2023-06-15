package com.example.wasteless.data

import com.example.wasteless.model.dummymodel.FakeDataSource
import com.example.wasteless.model.dummymodel.Food
import com.example.wasteless.model.dummymodel.History
import com.example.wasteless.model.dummymodel.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class UserRepository {

    private val user = mutableListOf<User>()
    private val history = mutableListOf<History>()
    private val foodDonate = mutableListOf<Food>()

    init {
        if (user.isEmpty()){
            FakeDataSource.dummyUser.forEach {
                user.add(it)
            }
        }
        if (history.isEmpty()){
            FakeDataSource.dummyHistory.forEach {
                history.add(it)
            }
        }
        if (foodDonate.isEmpty()){
            FakeDataSource.dummyFood.forEach {
                foodDonate.add(it)
            }
        }
    }

    fun getUserById(id: Int): Flow<User>{
        return flowOf(user.first{
            it.id == id
        })
    }
    fun getHistoryFoodDonateById(userId: Int): Flow<List<Food>>{
        return flowOf(foodDonate.filter {
            it.user.id == userId
        })
    }

    companion object{
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository =
            instance ?: synchronized(this){
                UserRepository().apply {
                    instance = this
                }
            }
    }
}