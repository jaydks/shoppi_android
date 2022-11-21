package com.example.shoppi_android.ui

import android.icu.text.CaseMap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.shoppi_android.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import java.util.Observable
import java.util.Observer

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarTitle = view.findViewById<TextView>(R.id.toolbar_home_title)
        val toolbarIcon = view.findViewById<ImageView>(R.id.toolbar_home_icon)
        val viewpager = view.findViewById<ViewPager2>(R.id.viewpager_home_banner)
        val viewpagerIndicator = view.findViewById<TabLayout>(R.id.viewpager_home_banner_indicator)

        // json asset가져오기
        val assetLoader = AssetLoader()
        val homeJsonString =
            assetLoader.getJsonString(requireContext(), "home.json") // json포맷의 데이터를 문자열 타입으로 변경
        Log.d("homeData", homeJsonString ?: "")

        // json object로 변환
        if (!homeJsonString.isNullOrEmpty()) {
            val gson = Gson()
            val homeData = gson.fromJson(homeJsonString, HomeData::class.java)

            // 앞으로 viewModel이 state holder역할을 하면서 Data룰 저장하고 관리할 것
            // 따라서 Fragment에서 직접 title을 요청하는 것이 아닌 viewmodel의 title을 참조
            // 그리고 이를 LiveData가 제공하는 observe()메소드를 통해 데이터가 변경되었을 때 어떤 처리를 할 것인지 HomeFragment에서 구현해야함
            // 첫번째 인자로 lifecycleowner를 전달. fragment는 viewLifecycleOwner에 대한 참조를 얻을 수 있음.
            // lifecycleowner는 lifecycle이 변경되는 것에 대한 알림을 받아 현재의 lifecycle상태를 알고있는 객체를 의미
            // 두번째 인자로는 observer를 구현해서 전달해야 함. -> observer 객체를 만들기 위해 object:
            // title 객체가 변경되면 아래 람다 블럭이 호출되는 것. 따라서 람다 블럭 안에서 뷰를 업데이트하는 로직을 구현하면 됨.
            viewModel.title.observe(viewLifecycleOwner) { title ->
                toolbarTitle.text = title.text
                GlideApp.with(this).load(title.iconUrl).into(toolbarIcon)
            }

            viewModel.topBanner.observe(viewLifecycleOwner) { banners ->
                // 뷰페이저 어댑터 연결
                viewpager.adapter = HomeBannerAdaptor().apply {
                    submitList(banners)
                }
            }


            // 다음 페이지 보이기
            val pageWidth =
                resources.getDimension(R.dimen.viewpager_item_width) // getDimension() : dp값을 픽셀로 변환해서 반환해줌
            val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
            val screenWidth = resources.displayMetrics.widthPixels // 안드로이드 기기 가로길이
            val offset = screenWidth - pageWidth - pageMargin

            viewpager.offscreenPageLimit =
                3 // offscreenPageLimit : 현재 보여지는 페이지의 양 사이드에 몇 개의 페이지까지 유지할것인지 값을 설정하는 메서드
            viewpager.setPageTransformer { page, position ->
                page.translationX = position * -offset // 다음 페이지는 원래 위치보다 왼쪽으로 이동해야 하므로 음수로 계산
            }

            // 인디케이터_탭레이아웃의 기능을 이용해 구현함
            TabLayoutMediator(viewpagerIndicator, viewpager) { tab, position -> // 탭의 위치정보를 position으로 전달받을 수 있음
                // 특정 위치에서 탭의 스타일을 변경해야 하는 경우 람다블럭 내부에서 처리
            }.attach()
        }
    }
}