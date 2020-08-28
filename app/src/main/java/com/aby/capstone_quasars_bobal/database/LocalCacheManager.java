package com.aby.capstone_quasars_bobal.database;

import android.content.Context;
import android.util.Log;


import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.aby.capstone_quasars_bobal.interfaces.MainViewInterface;

import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LocalCacheManager {
    private Context context;
    private static LocalCacheManager _instance;
    private AppDatabase db;

    public static LocalCacheManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new LocalCacheManager(context);
        }
        return _instance;
    }

    public LocalCacheManager(Context context) {
        this.context = context;
        db = AppDatabase.getAppDatabase(context);
    }

    public void getTests(final MainViewInterface mainViewInterface) {
        db.speakingTestDoa()
                .getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SpeakingTest>>() {
                    @Override
                    public void accept(List<SpeakingTest> speakingTests) throws Exception {
                        mainViewInterface.onTestLoaded(speakingTests);
                    }
                });
    }

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        public void onCreate(SupportSQLiteDatabase db) {

            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                @Override
                public void run() {
//                    addTests(context);
                    System.out.println("Insert called");

                }
            });
        }
    };

    private void addTests(Context context){
        System.out.println("Insert called");

    }





    public void addTests(final MainViewInterface addNoteViewInterface, SpeakingTest speakingTest) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                db.speakingTestDoa().insertAll(speakingTest);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                addNoteViewInterface.onTestAdded();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("ERROR", "onError: " + e);
                addNoteViewInterface.onDataNotAvailable();
            }
        });
    }


}
