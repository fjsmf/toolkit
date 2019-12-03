package ss.com.toolkit.archimvp.presenter;

import android.content.Context;
import android.util.Log;

import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import ss.com.toolkit.archimvp.ArchiApplication;
import ss.com.toolkit.archimvp.model.GithubService;
import ss.com.toolkit.archimvp.model.User;
import ss.com.toolkit.archimvp.view.RepositoryMvpView;

public class RepositoryPresenter implements Presenter<RepositoryMvpView> {

    private static final String TAG = "RepositoryPresenter";

    private RepositoryMvpView repositoryMvpView;
    private Subscription subscription;

    @Override
    public void attachView(RepositoryMvpView view) {
        this.repositoryMvpView = view;
    }

    @Override
    public void detachView() {
        this.repositoryMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadOwner(String userUrl) {
        GithubService githubService = getGithubService();
        subscription = githubService.userFromUrl(userUrl)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(defaultSubscribeScheduler())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Log.i(TAG, "Full user data loaded " + user);
                        repositoryMvpView.showOwner(user);
                    }
                });
    }

    private GithubService githubService;
    private Scheduler defaultSubscribeScheduler;

    public static ArchiApplication get(Context context) {
        return (ArchiApplication) context.getApplicationContext();
    }

    public GithubService getGithubService() {
        if (githubService == null) {
            githubService = GithubService.Factory.create();
        }
        return githubService;
    }

    //For setting mocks during testing
    public void setGithubService(GithubService githubService) {
        this.githubService = githubService;
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }
}
