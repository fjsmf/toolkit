package java_;

import okhttp3.Call;

public class SynchronizedTest {
    interface CallBack {
        void onCallBack();
    }

    class A implements CallBack {

        public A() {

        }

        @Override
        public void onCallBack() {

        }
    }


    public static void main(String[] args) throws Exception {
    }
}
