package com.nunnos.keepintouch.base.baseview;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;

import com.nunnos.keepintouch.R;
import com.nunnos.keepintouch.base.baseview.base.viewmodel.BaseViewModelActivity;

public abstract class BaseActivityViewModelLiveData<VM extends ViewModel & LifecycleObserver, DB extends ViewDataBinding> extends BaseViewModelActivity implements LifecycleObserver {
    private enum Animation {
        NO_ANIMATION,
        SLIDING_UP
    }

    public final static String RESULT = "RESULT";

    private static final String TAG = "BaseActivityVMLiveData";
    protected VM viewModel;
    protected DB dataBinding;
    private final String ENTRANCE_ANIMATION = "ENTRANCE_ANIMATION";
    private Animation entranceAnimation;

    protected abstract @LayoutRes
    int layout();

    protected abstract void dataBindingViewModel();

    protected abstract Class<VM> getClassViewModel();

    public VM getShareViewModel() {
        return viewModel;
    }

    private ActivityResultLauncher<Intent> resultLauncher;

    private OnActivityResult onActivityResultListener;

    /***************************************************
     * LIFECYCLE
     * *************************************************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = (VM) getViewModel();
        dataBinding = DataBindingUtil.setContentView(this, layout());
        dataBinding.setLifecycleOwner(this);
        dataBindingViewModel();
        if (getIntent().hasExtra(ENTRANCE_ANIMATION)) {
            entranceAnimation = (Animation) getIntent().getSerializableExtra(ENTRANCE_ANIMATION);
        }
        setResultLauncher();
    }

    private void setResultLauncher() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent intent = result.getData();

                    if (intent != null) {
                        if (intent.getBooleanExtra(RESULT, false)) {
                            onActivityResultListener.onResultOK();
                        } else {
                            onActivityResultListener.onResultKO();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModel != null) {
            getLifecycle().addObserver(viewModel);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (viewModel != null) {
            getLifecycle().removeObserver(viewModel);
        }
    }

    public void overrideSlidingTransition(Fragment fragment, @AnimatorRes @AnimRes int enter, @AnimatorRes @AnimRes int exit) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enter, exit);
        transaction.addToBackStack(null);
        transaction.replace(R.id.activity_fragment_container, fragment);
        transaction.commit();
    }

    private void overrideSlidingTransition(@AnimatorRes @AnimRes int enter, @AnimatorRes @AnimRes int exit) {
        overridePendingTransition(enter, exit);
    }

    public void overrideSlidingUpTransition(Fragment fragment) {
        overrideSlidingTransition(fragment, R.anim.slide_in_up, R.anim.slide_out_down);
    }

    public void overridePopTransition(Fragment fragment) {
        overrideSlidingTransition(fragment, R.anim.pop_in_animation, R.anim.pop_in_animation);
    }

    public void launchSlidingUpActivity(Intent intent) {
        intent.putExtra(ENTRANCE_ANIMATION, Animation.SLIDING_UP);
        startActivity(intent);
        overrideSlidingTransition(R.anim.slide_in_up, R.anim.stay);
    }

    public void launchSlidingUpActivityForResult(ActivityResultLauncher<Intent> resultLauncher, Intent intent) {
        resultLauncher.launch(intent);
        overrideSlidingTransition(R.anim.slide_in_up, R.anim.stay);
    }

    @Override
    public void finish() {
        super.finish();
        //TODO: Recollir quina animació s'ha fet al cridar-la, fer switch case on es possi l'opossada al tancar
        if (entranceAnimation == null) return;
        switch (entranceAnimation) {
            case SLIDING_UP:
                overrideSlidingTransition(R.anim.stay, R.anim.slide_out_down);
                break;
            case NO_ANIMATION:
            default:
                //Do nothing
        }
    }

    public ActivityResultLauncher<Intent> getResultLauncher() {
        return resultLauncher;
    }

    public void setOnActivityResultListener(OnActivityResult onActivityResultListener) {
        this.onActivityResultListener = onActivityResultListener;
    }

    public interface OnActivityResult {
        void onResultOK();

        void onResultKO();
    }
}
