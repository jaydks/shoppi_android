package com.example.shoppi_android.ui.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppi_android.AssetLoader
import com.example.shoppi_android.repository.HomeAssetDataSource
import com.example.shoppi_android.repository.HomeRepository
import com.example.shoppi_android.ui.home.HomeViewModel


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    // create 메소드에서 HomeViㅂewModel을 생성하고, 반환하면 됨
    // 파라미터인 modelClass는 Class<T>타입이고, T는 ViewModel클래스의 서브 클래스여야 함.
    // => HomeViewMpdel같이 ViewModel의 서브클래스만 이 create 메소드를 통해 구현이 가능함
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) { // isAssignableFrom() : 제네링 타입으로 전달받은 인자의 타입을 알 수 있음. 따라서 HomeViewModel타입인지 확인해보겠음
            //HomeViewModel타입이 맞다면 HomeViewModel객체를 생성하면 됨
            val repository = HomeRepository(HomeAssetDataSource(AssetLoader(context)))
            // return HomeViewModel(repository)
            // HomeViewModel은 T의 제약조건을 만족하는 class이므로 class캐스팅을 as를 사용해서 바로 해도 됨
            return HomeViewModel(repository) as T
        } else { // HomeViewModel이 아닌 클래스에서 create를 혹시 호출하게 된다면 에러 던짐
            throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
        }
    }
}