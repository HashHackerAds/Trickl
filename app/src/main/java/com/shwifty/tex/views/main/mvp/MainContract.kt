package com.shwifty.tex.views.main.mvp

import android.content.Context
import android.content.Intent
import com.schiwfty.torrentwrapper.models.TorrentFile
import com.schiwfty.torrentwrapper.repositories.ITorrentRepository
import com.shwifty.tex.utils.CONNECTIVITY_STATUS
import com.shwifty.tex.views.base.mvp.BaseContract

/**
 * Created by arran on 16/04/2017.
 */
interface MainContract {
    interface View : BaseContract.MvpView {
        fun showTorrentInfoActivity(infoHash: String)
        fun showAddTorrentActivity(hash: String? = null, magnet: String? = null, torrentFilePath: String? = null)
        fun showNoWifiDialog(torrentFile: TorrentFile)
        fun getConnectivityStatus(): CONNECTIVITY_STATUS
        fun startFileDownloading(torrentFile: TorrentFile, torrentRepository: ITorrentRepository)
        fun openTorrentFile(torrentFile: TorrentFile, torrentRepository: ITorrentRepository)
        fun openDeleteTorrentDialog(torrentFile: TorrentFile)
        fun showChromecastController(show: Boolean)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun showAddTorrentActivity(hash: String? = null, magnet: String? = null, torrentFilePath: String? = null)
        fun showTorrentInfoActivity(infoHash: String)
        fun handleIntent(intent: Intent)
        fun checkStatusForDownload(torrentFile: TorrentFile)
        fun startChromecast(torrentFile: TorrentFile)
        fun initializeCastContext(context: Context)
        fun addSessionListener()
        fun removeSessionListener()
    }
}