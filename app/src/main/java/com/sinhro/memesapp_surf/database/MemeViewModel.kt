package com.sinhro.memesapp_surf.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sinhro.memesapp_surf.customDebugger.CustomDebug
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
            dao.insert(meme).subscribeOn(Schedulers.io()).subscribe(
                {
                    CustomDebug.log("meme added to db successfully")
                },
                {
                    CustomDebug.log("meme not added to db")
                    it.printStackTrace()
                }
            )
    }

    fun get(id:Long): Single<MemeInfo> {
        return dao.getById(id)
    }


    fun getObservableMemesList() : Observable<List<MemeInfo>> {
        return dao.getAllMemes()
    }
    //fun onMemesListChanged( action : (List<MemeInfo>) -> Unit){}
}