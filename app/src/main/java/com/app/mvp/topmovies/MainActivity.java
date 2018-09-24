package com.app.mvp.topmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.app.mvp.App;
import com.app.mvp.R;
import com.app.mvp.topmovies.MainActivityMVP.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View {

    @BindView(R.id.root_view)
    android.view.ViewGroup rootView;

    @BindView(R.id.recycler_view)
    android.support.v7.widget.RecyclerView recyclerView;

    @Inject
    MainActivityMVP.Presenter presenter;

    private ListAdapter listAdapter;
    private List<ViewModel> viewModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((App) getApplication()).getAppComponent().inject(this);


        viewModelList = new ArrayList<>();
        listAdapter = new ListAdapter(viewModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.loadData();
    }


    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxUnSubscribe();
        viewModelList.clear();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(ViewModel viewModel) {
        viewModelList.add(viewModel);
        listAdapter.notifyItemInserted(viewModelList.size() - 1);
        Log.d("updateData()", "size :"+viewModelList.size());
    }

    @Override
    public void showSnackBar() {

    }
}
