package com.schiwfty.tex.views.torrentfiles.mvp

import android.content.Context
import android.os.Bundle
import com.schiwfty.tex.models.TorrentInfo

/**
 * Created by arran on 7/05/2017.
 */
interface TorrentFilesContract {
    interface View {
        fun setupViewFromTorrentInfo(torrentInfo: TorrentInfo)
    }

    interface Presenter {
        fun setup(context: Context, view: TorrentFilesContract.View, arguments: Bundle?)
        fun loadTorrent(torrentHash: String)
        var torrentHash: String
    }
}