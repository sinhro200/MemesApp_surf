package com.sinhro.memesapp_surf.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sinhro.memesapp_surf.model.memes.MemeInfo
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MemeViewModel(application: Application): AndroidViewModel(application) {

    private var allMemes : Observable<List<MemeInfo>>
    private var dao : MemeDao
    init {
        val memesDao = MemesRoomDatabase.getDatabase(application).memesDao()
        dao = memesDao
        allMemes = memesDao.getAllMemes()
    }

    fun insert(meme : MemeInfo){
        Observable.just {
            dao.insert(meme)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun get(id:Long): Single<MemeInfo> {
        return dao.getById(id)
    }


    fun getObservableMemesList() = allMemes
    //fun onMemesListChanged( action : (List<MemeInfo>) -> Unit){}
}