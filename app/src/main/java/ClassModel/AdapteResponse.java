package ClassModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return null;
    }

    @Override
    public void onBindViewHolder(ResponseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public  static class ResponseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ResponseViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
