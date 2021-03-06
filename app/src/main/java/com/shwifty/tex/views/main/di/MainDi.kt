package com.shwifty.tex.views.main.di


import com.schiwfty.torrentwrapper.repositories.ITorrentRepository
import com.shwifty.tex.MyApplication
import com.shwifty.tex.Trickl
import com.shwifty.tex.TricklComponent
import com.shwifty.tex.chromecast.ICastHandler
import com.shwifty.tex.views.base.PresenterScope
import com.shwifty.tex.views.main.mvp.MainActivity
import com.shwifty.tex.views.main.mvp.MainContract
import com.shwifty.tex.views.main.mvp.MainPresenter
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Created by arran on 15/02/2017.
 */
@PresenterScope
@Component(modules = arrayOf(MainModule::class), dependencies = arrayOf(TricklComponent::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)
}

@Module
class MainModule {
    @Provides
    @PresenterScope
    internal fun providesMainPresenter(torrentRepository: ITorrentRepository): MainContract.Presenter {
        return MainPresenter(torrentRepository, MyApplication.castHandler)
    }

}

