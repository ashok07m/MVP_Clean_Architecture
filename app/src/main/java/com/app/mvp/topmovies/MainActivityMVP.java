package com.app.mvp.topmovies;

import io.reactivex.Observable;

public interface MainActivityMVP {


    interface View {
        void updateData(ViewModel viewModel);

        void showSnackBar();
    }

    interface Presenter {
        void setView(MainActivityMVP.View view);

        void loadData();

        void rxUnSubscribe();
    }

    interface Model {
        Observable<ViewModel> result();
    }
}
