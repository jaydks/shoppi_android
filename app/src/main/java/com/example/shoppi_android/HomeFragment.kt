package com.example.shoppi_android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.json.JSONObject

class HomeFragment : Fragment() {

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

            toolbarTitle.text = homeData.title.text
            GlideApp.with(this).load(homeData.title.iconUrl).into(toolbarIcon)

            // 뷰페이저 어댑터 연결
            viewpager.adapter = HomeBannerAdaptor().apply {
                submitList(homeData.topBanners)
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
