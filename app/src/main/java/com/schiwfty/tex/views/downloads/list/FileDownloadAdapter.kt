package com.schiwfty.tex.views.downloads.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.pawegio.kandroid.inflateLayout
import com.schiwfty.tex.R
import com.schiwfty.tex.models.TorrentFile
import com.schiwfty.tex.utils.getFullPath
import com.schiwfty.tex.utils.onClick
import com.schiwfty.tex.views.torrentfiles.list.TorrentFileCardHolder

/**
 * Created by arran on 19/04/2017.
 */
class FileDownloadAdapter(val itemClickListener: (TorrentFile, ClickTypes) -> Unit) : RecyclerView.Adapter<TorrentFileCardHolder>() {
    var torrentFiles: List<TorrentFile> = mutableListOf()

    companion object{
        enum class ClickTypes{
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TorrentFileCardHolder {
        val itemView = parent.context.inflateLayout(R.layout.list_item_torrent_file, parent, false)
        val holder = TorrentFileCardHolder(itemView)
        holder.onClick { view, position, type ->
           //TODO show popup with options or info?
        }
        return holder
    }

    override fun onBindViewHolder(holder: TorrentFileCardHolder, position: Int) {
        holder.bind(torrentFiles[position])
    }

    override fun getItemCount(): Int {
        return torrentFiles.size
    }

    fun updatePercentages(updatedDetails: List<Triple<String, String, Int>>){
        torrentFiles.forEach { file ->
            updatedDetails.forEach {
                if(file.torrentHash == it.first && file.getFullPath() == it.second)
                file.percComplete = it.third
            }
        }
    }
}