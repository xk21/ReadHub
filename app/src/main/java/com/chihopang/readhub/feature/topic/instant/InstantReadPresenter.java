package com.chihopang.readhub.feature.topic.instant;

import com.chihopang.readhub.base.mvp.INetworkPresenter;
import com.chihopang.readhub.model.InstantReadData;
import com.chihopang.readhub.network.ApiService;
import com.chihopang.readhub.network.HotTopicService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class InstantReadPresenter implements INetworkPresenter {
  private HotTopicService mService = ApiService.createHotTopicService();
  private InstantReadFragment mView;
  private String mTopicId;

  public InstantReadPresenter(InstantReadFragment mView) {
    this.mView = mView;
  }

  @Override public InstantReadFragment getView() {
    return mView;
  }

  @Override public void start() {
    request().observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<InstantReadData>() {
          @Override public void accept(@NonNull InstantReadData instantReadData) throws Exception {
            getView().onSuccess(instantReadData);
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(@NonNull Throwable throwable) throws Exception {
            throwable.printStackTrace();
            getView().onError(throwable);
          }
        });
  }

  @Override public void startRequestMore() {

  }

  @Override public Observable<InstantReadData> request() {
    return mService.getInstantRead(mTopicId);
  }

  @Override public Observable requestMore() {
    return null;
  }

  public void getInstantRead(String topicId) {
    mTopicId = topicId;
    start();
  }
}
