package com.nunnos.keepintouch.presentation.feature.main.activity.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nunnos.keepintouch.presentation.feature.main.MainNavigation;

public class MainNavigationViewModel extends ViewModel {

    protected MutableLiveData<Integer> navigation = new MutableLiveData<>();

    public MutableLiveData<Integer> getNavigation() {
        return navigation;
    }

    public void navigateToMain() {
        navigation.setValue(MainNavigation.MAIN);
    }
    public void navigateToNewContact() {
        navigation.setValue(MainNavigation.NEW_CONTACT);
    }
    public void navigateToContactInfo() {
        navigation.setValue(MainNavigation.CONTACT_INFO);
    }

    public void navigateToSearchContact() {
        navigation.setValue(MainNavigation.SEARCH_CONTACT);
    }


/*    protected <U extends BaseUseCaseLiveData, T, Params> void updateOnSuccess(U usecase, MediatorLiveData<T> model, Params params, boolean loading, boolean continueProgressWhenSuccess){
        LiveData<ResultData<T>> source = LiveDataReactiveStreams.fromPublisher(((Flowable<ResultData<T>>) usecase.doWork(params)))
                .onErrorReturns(throwable -> ResultData.error());
        model.addSource(source, resource -> {
            if(resource != null){
                switch (resource.getResultType()){
                    case StatusLiveResult.LIVE_ONSUCCESS:
                        model.postValue(T) resource.getData();
                        model.removeSource(source);
                        break;
                    case StatusLiveResult.LIVE_ONERROR:
                        handleError.postValue(resource);
                        model.removeSource(source);
                        break;
                }
            }
        });
    }*/
}
