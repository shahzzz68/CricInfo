package com.example.shahz.cricinfo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.shahz.cricinfo.R;
import com.example.shahz.cricinfo.model_class.First_inning_Details;


import java.util.List;

public class ListView_Adapter  extends ArrayAdapter<First_inning_Details> {

    List<First_inning_Details> inningDetails;
    Context context;

    public ListView_Adapter(@NonNull Context context, int resource, @NonNull List<First_inning_Details> objects) {
        super(context, resource, objects);
        this.context = context;
        this.inningDetails = objects;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return inningDetails.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.single_first_inning_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        String playerName=inningDetails.get(position).getName();
        holder.pname.setText(playerName);

        //get first letter of each String item
        String firstLetter = String.valueOf(playerName.charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(playerName);
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        holder.imageView.setImageDrawable(drawable);

        holder.pruns.setText(inningDetails.get(position).getRuns());
        holder.pballs.setText(inningDetails.get(position).getBallsPlayed());
        holder.pfours.setText(inningDetails.get(position).getFours());
        holder.psixs.setText(inningDetails.get(position).getSixs());
        holder.pout.setText(inningDetails.get(position).getOut());

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView pname;
        private TextView pruns;
        private TextView pballs;
        private TextView pfours;
        private TextView psixs;
        private TextView pout;

        public ViewHolder(View v) {
            imageView =  v.findViewById(R.id.image_view);
            pname =  v.findViewById(R.id.ptext);
            pruns =  v.findViewById(R.id.pRuns);
            pballs =  v.findViewById(R.id.pBalls);
            pfours =  v.findViewById(R.id.pfours);
            psixs =  v.findViewById(R.id.psixs);
            pout =  v.findViewById(R.id.poutStatus);
        }
    }
}