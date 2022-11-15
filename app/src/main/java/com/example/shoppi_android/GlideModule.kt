package com.example.shoppi_android

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule: AppGlideModule() {
}
//이렇게 하면 GlideApp이라는 클래스가 generated됨. 따라서 GlideApp.with로 시작하는 api를 사용가능함