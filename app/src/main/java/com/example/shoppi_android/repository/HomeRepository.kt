package com.example.shoppi_android.repository

import com.example.shoppi_android.model.HomeData

class HomeRepository(private val assetDataSource: HomeAssetDataSource) {

    fun getHomeData() : HomeData {
        return assetDataSource.getHomeData()
    }
}