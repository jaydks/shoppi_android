package com.example.shoppi_android.repository

import com.example.shoppi_android.model.HomeData

interface HomeDataSource {

    fun getHomeData() : HomeData
}