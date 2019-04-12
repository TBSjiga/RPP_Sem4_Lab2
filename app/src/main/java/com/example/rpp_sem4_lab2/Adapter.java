package com.example.rpp_sem4_lab2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.InputStream;

public class Adapter extends RecyclerView.Adapter<Adapter.CardAdapt>{

    private int icount;
    private Context context;

    public Adapter(int n, Context context) {
        this.context = context;
        this.icount = n;
    }

    @NonNull
    @Override
    public CardAdapt onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view, parent, false);

        return new CardAdapt(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapt holder, final int position) {

        final int index = position + 1;
        JSONArray data = DataHold.getInstance().getData();

        TextView text = holder.text;
        try {
            text.setText(data.getJSONObject(index).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView image = holder.image;

        DownloadImageTask imageLoad = new DownloadImageTask(image);
        String base_url = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/";
        try {
            imageLoad.execute(base_url + data.getJSONObject(index).getString("graphic"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        int backgroundColor = ContextCompat.getColor(holder.itemView.getContext(),
                (index) % 2 == 0 ? R.color.blue : R.color.white);

        holder.linearLayout.setBackgroundColor(backgroundColor);

        holder.view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ViewPagerClass.class);

                intent.putExtra("position", index-1);
                System.out.println(index-1);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return icount;
    }

    class CardAdapt extends RecyclerView.ViewHolder {

        public View linearLayout;
        private View view;
        private ImageView image;
        private TextView text;

        public CardAdapt(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.my_image);
            text = itemView.findViewById(R.id.my_text);
            linearLayout = itemView.findViewById(R.id.line);
            this.view = itemView;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bImag;

        public DownloadImageTask(ImageView bImag) {
            this.bImag = bImag;
        }

        protected Bitmap doInBackground(String... urls) {
            String showurl = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(showurl).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result != null) {
                bImag.setImageBitmap(result);
            }
        }
    }
}
