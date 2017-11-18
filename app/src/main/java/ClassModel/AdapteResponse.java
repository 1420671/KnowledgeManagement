package ClassModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import DataModel.DataResponse;
import Interfaces.RecyclerViewOnItemClickListener;
import pe.edu.utp.knowledgemanagement.R;

/**
 * Created by Hypnos on 18/11/2017.
 */

public class AdapteResponse extends RecyclerView.Adapter<AdapteResponse.ResponseViewHolder> {
    private List<DataResponse> items;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapteResponse(List<DataResponse> items, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.items = items;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public ResponseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_response, parent, false);
        ResponseViewHolder pvh = new ResponseViewHolder(row, recyclerViewOnItemClickListener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ResponseViewHolder holder, int position) {
        holder.response.setText(items.get(position).getResponse());
        holder.vote.setText(items.get(position).getVote());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public  static class ResponseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
        TextView response, vote;
        public ResponseViewHolder(View itemView, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
            super(itemView);
            response = (TextView) itemView.findViewById(R.id.textView_Response);
            vote = (TextView) itemView.findViewById(R.id.textView_Vote);
            this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition(),null);
        }
    }
}
