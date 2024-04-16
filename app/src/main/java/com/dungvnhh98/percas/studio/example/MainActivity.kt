package com.dungvnhh98.percas.studio.example

import android.os.Bundle
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

        binding.btnBanner.setOnClickListener {
            AdmobManager.loadBanner(this, "", binding.flBanner, object: BannerCallBack{
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
}