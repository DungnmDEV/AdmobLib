package com.dungvnhh98.percas.studio.example

import android.R
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AlignmentSpan
import android.text.style.ImageSpan
import androidx.appcompat.app.AppCompatActivity
import com.dungvnhh98.percas.studio.admoblib.AdmobManager
import com.dungvnhh98.percas.studio.admoblib.ViewControl.gone
import com.dungvnhh98.percas.studio.admoblib.ViewControl.visible
import com.dungvnhh98.percas.studio.admoblib.callback.BannerCallBack
import com.dungvnhh98.percas.studio.example.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdView


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val text = "Đây là một đoạn văn bản với hình ảnh."
//        val builder = SpannableStringBuilder(text)
//        val drawable = getDrawable(R.drawable.ic_delete)
//        drawable?.setBounds(0, 0, 50, 50) // Cài đặt kích thước cho hình ảnh
//
//        val imageSpan = ImageSpan(drawable!!, ImageSpan.ALIGN_BOTTOM)
//        builder.setSpan(imageSpan, 15, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        binding.tv.text = builder

        customText()

        binding.btnBanner.setOnClickListener {
            AdmobManager.loadBanner(this, "", binding.flBanner, object : BannerCallBack {
                override fun onAdLoaded() {
                    binding.flBanner.visible()
                    binding.line.visible()
                }

                override fun onAdFailedToLoad(message: String) {
                    binding.flBanner.gone()
                    binding.line.gone()
                }

                override fun onAdClicked() {
                }

                override fun onPaid(adValue: AdValue, mAdView: AdView) {
                }

            })
        }
    }

    fun customText() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_delete)

        // Chuyển đổi ảnh thành drawable
        val drawable = BitmapDrawable(resources, bitmap)

        val text1 = "Click the button "
        val text2 = " to add sound to your favorites"

        // Tạo một SpannableStringBuilder
        val spannableString = SpannableString(" ")
        val imageSpan = ImageSpan(drawable, ImageSpan.ALIGN_BASELINE)
        drawable.setBounds(0, 0, binding.tv.lineHeight, binding.tv.lineHeight)
        spannableString.setSpan(imageSpan, 0, 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Căn giữa văn bản và hình ảnh
        val alignmentSpan = AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER)
        spannableString.setSpan(
            alignmentSpan,
            0,
            spannableString.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val spannableStringBuilder = SpannableStringBuilder()
        spannableStringBuilder.append(text1)
        spannableStringBuilder.append(spannableString)
        spannableStringBuilder.append(text2)


        binding.tv.text = spannableStringBuilder
    }
}