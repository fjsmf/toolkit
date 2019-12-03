package ss.com.toolkit.archimvp.presenter;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
