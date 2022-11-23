package com.example.shoppi_android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppi_android.model.Banner
import com.example.shoppi_android.model.Title
import com.example.shoppi_android.repository.HomeRepository

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
    // 홈화면을 그리는 데 필요한 데이터의 holder 역할을 하는 클래스가 되어야 함.
    // 따라서 HomeData의 title객체, listBanner객체를 관리하는 변수를 추가해야 함

    private val _title = MutableLiveData<Title>() // 외부에서 접근하지 못하는 경우 _를 앞에 붙이는 네이밍 컨벤션 존재
    val title: LiveData<Title> = _title // 공개하는 변수. _title이 title에 할당이 될 때 LiveData로 변환되어 할당됨

    private val _topBanners = MutableLiveData<List<Banner>>()
    val topBanner: LiveData<List<Banner>> = _topBanners

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        // 실제 구현은 HomeViewModel에서 하면 안 됨
        // HomeRepository의 getHomeData 메소드를 호출. 그러기 위해선 HomeRepository 객체가 생성되어 있어야 함. 따라서 HomeViewModel의 생성자로 받겠음.
        // 왜 loadHomeData()내부에서 안 만들고 생성자로 받지? -> 앞서 assetloader를 생성자로 추가한 설명과 동일
        // => homeViewModel은 앞으로 데이터를 요청하게 되는 경우 모두 HomeRepository에게 데이터를 요청할 것. 따라서 Body에서 생성하는 것보다 HomeViewModel을 생성할 때 생성자로 받아서 재사용하는 편이 나음
        // TODO Data Layer - Repository 에 요청
        val homeData = homeRepository.getHomeData()
        // homeData가 null이 아니라면, _title과 _topBanners의 값을 변경
        homeData.let{ homeData ->
            _title.value = homeData.title
            _topBanners.value = homeData.topBanners
        }
    }
}