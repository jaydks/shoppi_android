package com.example.shoppi_android.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppi_android.model.Banner
import com.example.shoppi_android.GlideApp
import com.example.shoppi_android.R
import com.example.shoppi_android.databinding.ItemHomeBannerBinding
import java.text.DecimalFormat
import kotlin.math.roundToInt


class HomeBannerAdaptor :
    ListAdapter<Banner, HomeBannerAdaptor.HomeBannerViewHolder>(BannerDiffCallback()) {
    // ListAdaptor는 데이터의 리스트를 받아서 0번째부터 순차적으로 viewHolder와 바인딩을 함. 이 때 레이아웃은 그대로 있고 데이터만 업데이트된다면 효율적 -> 이를 지원하는 것이 listadaptor
    // DiffUtil.ItemCallback을 구현 -> 스크롤이 변경됨에 땨라서 데이터가 실제로 변경되는지 확인하고 데이터가 한 명이 되면 그때서야 레이아웃을 업데이트 함
    // 이 때 어떠한 아이디를 기준으로 같다/다르다를 구분할건지 DiffUtil.ItemCallback을 구현하면서 정의해야 함

    private lateinit var binding: ItemHomeBannerBinding

    // view는 HomeBanner에서 inflate시킬 레이아웃을 의미
    // onCreateViewHolder에서는 레이아웃을 inflate해서 view를 만들고, ViewHolder에 전달해서
    // ViewHolder()를 생성해서 return
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        binding = ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeBannerViewHolder(binding)
    }

    // onCreateViewHolder가 호출되어 생성한 ViewHolder가 여기서 전달됨
    // ViewHolder에 데이터를 바인딩 해야함
    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(getItem(position)) // 해당 포지션의 데이터를 전달해줌
    }

    //private val binding: ItemHomeBannerBinding
    // binding을 전달해야 내부에서 bind를 쓸 수 있고, val 로 변수 선언을 해야 내부에서 binding을 쓸 수 있음. 그리고 내부에서만 쓸거라서 private
    class HomeBannerViewHolder(private val binding: ItemHomeBannerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(banner: Banner) { // 인자로 배너 객체를 받아서 view와 바인딩을 해줘야 함
            binding.banner = banner
            binding.executePendingBindings() // 바로 데이터가 바인딩됨
        }
    }
}

class BannerDiffCallback :
    DiffUtil.ItemCallback<Banner>() { // 두 아이템을 비교하는 기준을 제공. 두 아이템이 다르다고 판단되면 새로운 아이템을 그림(업데이트)
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.productDetail.productId == newItem.productDetail.productId // 두 아이디가 동일하다면 아래 메서드가 실행됨
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem// 다른 프로퍼티의 값들도 모두 동일한지 확인. 즉, 아이템의 동등석을 비교
    }

}