package com.example.shoppi_android.repository

import com.example.shoppi_android.AssetLoader
import com.example.shoppi_android.model.HomeData
import com.google.gson.Gson

// 생성자로 AssetLoader를 추가하는 이유?
// HomeAssetDataSource 클래스는 asset을 기반으로 데이터를 반환할 것
// -> AssetLoader는 getHomeData 뿐만 아니라 다른 메서드에서도 활용될 가능성이 높음
// => 매번 메서드의 바디에서 AssetLoader를 생성하기 보다는 HomeAssetDataSource를 최초에 생성할 때 생성자로 받아서 재사용하는 편이 낫다고 생각
class HomeAssetDataSource(private val assetLoader: AssetLoader) : HomeDataSource {
    // gson도 함수 본문이 아닌 클래스의 멤버변수로 추가하는 편이 나아보임
    private val gson = Gson()

    override fun getHomeData(): HomeData {
        // json asset가져오기
        // val homeJsonString = assetLoader.getJsonString("home.json") // json포맷의 데이터를 문자열 타입으로 변경
        return assetLoader.getJsonString("home.json").let{ homeJsonString ->
            gson.fromJson(homeJsonString, HomeData::class.java)
        }
    }
}