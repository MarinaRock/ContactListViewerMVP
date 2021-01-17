package ru.marina.contactlistviewermvp

import androidx.multidex.MultiDexApplication
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import ru.marina.contactlistviewermvp.di.DI
import ru.marina.contactlistviewermvp.di.appModule
import toothpick.Toothpick
import toothpick.configuration.Configuration

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        initToothpick()
        initFresco()
    }

    private fun initToothpick() {
        Toothpick.setConfiguration(Configuration.forProduction().preventMultipleRootScopes())
        Toothpick.openScope(DI.APP_SCOPE).installModules(appModule(this))
    }

    private fun initFresco() {
        val diskCacheConfig = DiskCacheConfig.newBuilder(this)
            .setMaxCacheSize(30L * ByteConstants.MB)
            .setMaxCacheSizeOnLowDiskSpace(15L * ByteConstants.MB)
            .setMaxCacheSizeOnVeryLowDiskSpace(5L * ByteConstants.MB)
            .build()
        val frescoConfig = ImagePipelineConfig.newBuilder(this)
            .setDownsampleEnabled(true)
            .setMainDiskCacheConfig(diskCacheConfig)
            .setSmallImageDiskCacheConfig(diskCacheConfig)
            .build()
        Fresco.initialize(this, frescoConfig)
    }
}