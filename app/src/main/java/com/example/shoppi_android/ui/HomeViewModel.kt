package com.example.shoppi_android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppi_android.Banner
import com.example.shoppi_android.Title

class HomeViewModel : ViewModel() {
    // 홈화면을 그리는 데 필요한 데이터의 holder 역할을 하는 클래스가 되어야 함.
    // 따라서 HomeData의 title객체, listBanner객체를 관리하는 변수를 추가해야 함

    private val _title = MutableLiveData<Title>() // 외부에서 접근하지 못하는 경우 _를 앞에 붙이는 네이밍 컨벤션 존재
    val title: LiveData<Title> = _title // 공개하는 변수. _title이 title에 할당이 될 때 LiveData로 변환되어 할당됨

    private val _topBanners = MutableLiveData<List<Banner>>()
    val topBanner: LiveData<List<Banner>> = _topBanners



    fun loadHomeData() {
        // 실제 구현은 HomeViewModel에서 하면 안 됨
        // TODO Data Layer - Repository 에 요청
    }
}