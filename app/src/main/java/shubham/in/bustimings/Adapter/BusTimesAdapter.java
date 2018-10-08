package shubham.in.bustimings.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import shubham.in.bustimings.POJO.BusTimes;
import shubham.in.bustimings.R;

public class BusTimesAdapter extends RecyclerView.Adapter<BusTimesAdapter.MyViewHolder>{

    private Context context;
    private List<BusTimes.DataEntity> busTimesList;

    public BusTimesAdapter(Context context, List<BusTimes.DataEntity> busTimesList){
        this.context = context;
        this.busTimesList = busTimesList;
    }

    public void setBusTimesList(List<BusTimes.DataEntity> busTimesList){
        this.busTimesList = busTimesList;
        notifyDataSetChanged();
    }

    @Override
    public BusTimesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_transactions, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusTimesAdapter.MyViewHolder holder, int position) {

        BusTimes.DataEntity dataEntity = busTimesList.get(position);

        holder.fromStop.setText(dataEntity.getFromstop());
        holder.toStop.setText(dataEntity.getTostop());
        holder.fromTime.setText(dataEntity.getFromtime());
        holder.toTime.setText(dataEntity.getTotime());
    }

    @Override
    public int getItemCount() {
        if (busTimesList != null){
            return busTimesList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView fromStop;
        TextView toStop;
        TextView fromTime;
        TextView toTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            fromStop = (TextView) itemView.findViewById(R.id.fromStop);
            toStop = (TextView) itemView.findViewById(R.id.toStop);
            fromTime = (TextView) itemView.findViewById(R.id.fromTime);
            toTime = (TextView) itemView.findViewById(R.id.toTime);
        }
    }
}
