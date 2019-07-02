package java_;

import android.os.Process;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.concurrent.atomic.AtomicBoolean;

public class RxjavaDemo {
    public static void main(String[] args) {
        final RxjavaDemo userCancelDemo = new RxjavaDemo();
        Subscription subscription = userCancelDemo.create()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.newThread())
                .subscribe();

//        subscription.unsubscribe();

        while (true) ;
    }

    private AtomicBoolean mCompleteOccurs = new AtomicBoolean(false);

    private AtomicBoolean mErrorOccurs = new AtomicBoolean(false);

    private Subscriber mSubscriber;

    public Observable<String> create() {
        return Observable.<String>create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {

                }

                System.out.println("System.out call..."+ Process.myTid());
                subscriber.onNext("aaaaaaa");
                subscriber.onCompleted();
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (mSubscriber.isUnsubscribed()) {
                    System.out.println("System.out doOnError: user have cancel subscription..."+ Process.myTid());
                    return;
                }

                System.out.println("System.out doOnError: error occurs " + throwable+ Process.myTid());

                mErrorOccurs.set(true);
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                if (mSubscriber.isUnsubscribed()) {
                    System.out.println("System.out doOnCompleted: user have cancel subscription..."+ Process.myTid());
                    return;
                }
                System.out.println("System.out doOnCompleted: onCompleted."+ Process.myTid());
                mCompleteOccurs.set(true);
            }
        }).doOnUnsubscribe(new Action0() {
            @Override
            public void call() {
                if (mErrorOccurs.get() || mCompleteOccurs.get()) {
                    System.out.println("System.out doOnUnsubscribe: rxjava auto unsubscribe..."+ Process.myTid());
                } else {
                    System.out.println("System.out doOnUnsubscribe: user cancel subscription..."+ Process.myTid());
                }
            }
        }).lift(new Observable.Operator<String, String>() {
            @Override
            public Subscriber<? super String> call(Subscriber<? super String> subscriber) {
                mSubscriber = subscriber;
                return mSubscriber;
            }
        });
    }
}