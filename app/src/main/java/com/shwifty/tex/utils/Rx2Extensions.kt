package com.shwifty.tex.utils

import io.reactivex.Emitter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by arran on 30/04/2017.
 */
fun <T> Observable<T>.composeIo(): Observable<T> = compose<T>({
    it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
})

fun <T> createObservableFrom(event: (Emitter<T>) -> Unit): Observable<T> {
    return Observable.create<T> { emitter ->
        event.invoke(emitter)
    }
}
