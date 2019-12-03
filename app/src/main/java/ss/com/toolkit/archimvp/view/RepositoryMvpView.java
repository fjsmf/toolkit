package ss.com.toolkit.archimvp.view;


import ss.com.toolkit.archimvp.model.User;

public interface RepositoryMvpView extends MvpView {

    void showOwner(final User owner);

}
