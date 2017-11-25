package ClassModel;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import DataModel.DataPoll;
import Interfaces.FloatingActionButtonClick;
import pe.edu.utp.knowledgemanagement.R;

/**
 * Created by Hypnos on 25/11/2017.
 */

public class AdapterPoll extends RecyclerView.Adapter<AdapterPoll.PollViewHolder> {
    protected ArrayList<DataPoll> items;
    private FloatingActionButtonClick floatingActionButtonClick;

    public AdapterPoll(ArrayList<DataPoll> items, FloatingActionButtonClick floatingActionButtonClick) {
        this.items = items;
        this.floatingActionButtonClick = floatingActionButtonClick;
    }

    @Override
    public PollViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rows_poll, viewGroup, false);
        PollViewHolder rvh = new PollViewHolder(row, floatingActionButtonClick);

        return rvh;
    }

    @Override
    public void onBindViewHolder(PollViewHolder pollViewHolder, int i) {
        pollViewHolder.image.setImageBitmap(items.get(i).getPhoto());
        pollViewHolder.vote.setText(items.get(i).getVotos());
        pollViewHolder.response.setText(items.get(i).getRespuestas());
        pollViewHolder.title.setText(items.get(i).getTitulo());

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class PollViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private FloatingActionButtonClick floatingActionButtonClick;
        private ImageView image;
        private TextView vote, response, title;
        FloatingActionButton floatingButton1, floatingButton2, floatingButton3;
        public PollViewHolder(View itemView, FloatingActionButtonClick floatingActionButtonClick) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView_Poll);
            floatingButton1 = (FloatingActionButton) itemView.findViewById(R.id.floatingButton1);
            floatingButton2 = (FloatingActionButton) itemView.findViewById(R.id.floatingButton2);
            floatingButton3 = (FloatingActionButton) itemView.findViewById(R.id.floatingButton3);
            vote = (TextView) itemView.findViewById(R.id.textView_Vote);
            response = (TextView) itemView.findViewById(R.id.textView_Response);
            title = (TextView) itemView.findViewById(R.id.textView_Title);
            floatingButton1.setOnClickListener(this);
            floatingButton2.setOnClickListener(this);
            floatingButton3.setOnClickListener(this);
            this.floatingActionButtonClick = floatingActionButtonClick;
        }

        @Override
        public void onClick(View v) {
            floatingActionButtonClick.onClick(v, getAdapterPosition());

        }
    }
}
