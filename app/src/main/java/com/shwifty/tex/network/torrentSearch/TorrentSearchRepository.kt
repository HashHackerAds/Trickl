package com.shwifty.tex.network.torrentSearch

import com.shwifty.tex.models.TorrentSearchCategory
import com.shwifty.tex.models.TorrentSearchResult
import com.shwifty.tex.models.TorrentSearchSortType
import com.shwifty.tex.utils.composeIo
import rx.Observable

/**
 * Created by arran on 27/10/2017.
 */
internal class TorrentSearchRepository(private val torrentSearchApi: TorrentSearchApi): ITorrentSearchRepository {
    override fun search(searchTerm: String, sortType: TorrentSearchSortType, pageNumber: Int, category: TorrentSearchCategory): Observable<List<TorrentSearchResult>> {
        return torrentSearchApi.search(searchTerm,sortType, pageNumber, category)
                .map { emptyList<TorrentSearchResult>() }
                .composeIo()
    }
}