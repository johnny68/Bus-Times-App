package shubham.in.bustimings.UI.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shubham.in.bustimings.Adapter.BusTimesAdapter;
import shubham.in.bustimings.POJO.APIClient;
import shubham.in.bustimings.POJO.APIInterface;
import shubham.in.bustimings.POJO.BusTimes;
import shubham.in.bustimings.R;

public class FragmentTransaction extends Fragment{

    RecyclerView recyclerView;
    private ArrayList<BusTimes.DataEntity> busTimesArrayList = new ArrayList<>();
    private View view;
    private static ProgressDialog progressDialog;
    BusTimesAdapter busTimesAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        view = layoutInflater.inflate(R.layout.fragment_transactions, viewGroup, false);
        progressDialog = new ProgressDialog(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        busTimesAdapter = new BusTimesAdapter(getContext(), busTimesArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(busTimesAdapter);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<BusTimes> call = apiInterface.getBusTimes();
        call.enqueue(new Callback<BusTimes>() {
            @Override
            public void onResponse(Call<BusTimes> call, Response<BusTimes> response) {
                    busTimesArrayList.addAll(response.body().getData());
                    Log.d("5", "Passing to Adapter");
                   try {
                       busTimesAdapter.setBusTimesList(busTimesArrayList);
                       Log.d("4", "Passed to Adapter");

                   }catch (Exception exception){
                       exception.printStackTrace();
                   }
            }

            @Override
            public void onFailure(Call<BusTimes> call, Throwable t) {
                Log.d("Sorry", "Couldnt");
            }
        });


        return view;
    }
}
