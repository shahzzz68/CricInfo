//package com.example.shahz.cricinfo.Adapters;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.amulyakhare.textdrawable.TextDrawable;
//import com.amulyakhare.textdrawable.util.ColorGenerator;
//import com.example.shahz.cricinfo.R;
//import com.mikhaellopez.circularimageview.CircularImageView;
//
//import java.util.List;
//
//
//public class First_Inning_Adapter extends RecyclerView.Adapter<First_Inning_Adapter.ViewHolder> {
//
//    List<String> list;
//    Context context;
//
//    public First_Inning_Adapter(List<String> list, Context context) {
//        this.list=list;
//        this.context = context;
//    }
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_first_inning_item,parent,false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
////        String names=list.get(position).toString();
//        holder.textView.setText(names);
//
//        String firstLetter = String.valueOf(names.charAt(0));
//
//        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//        // generate random color
//        int color = generator.getColor(Integer.parseInt(list.get(position)));
//        //int color = generator.getRandomColor();
//
//        TextDrawable drawable = TextDrawable.builder()
//                .buildRound(firstLetter, color); // radius in px
//
//        holder.circleImageView.setImageDrawable(drawable);
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder
//    {
//        ImageView circleImageView;
//        TextView textView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            circleImageView=itemView.findViewById(R.id.image_view);
//            textView=itemView.findViewById(R.id.ptext);
//
//
//
//        }
//    }
//}
