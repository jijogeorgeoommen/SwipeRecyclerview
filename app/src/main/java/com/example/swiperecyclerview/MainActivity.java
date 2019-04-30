package com.example.swiperecyclerview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rview;
    private Paint p = new Paint();
    VerticalAdapter vadapter;
    ArrayList<String>fruitslist;
    private int[]imglist={R.drawable.apples,R.drawable.orange,R.drawable.grapes,R.drawable.watermelon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rview=findViewById(R.id.recyclerviewxml);
        fruitslist=new ArrayList<String>();

        fruitslist.add("Apples");
        fruitslist.add("ORANGE");
        fruitslist.add("GRAPES");
        fruitslist.add("WATERMELON");

        vadapter=new VerticalAdapter(fruitslist);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);

        rview.setAdapter(vadapter);
        rview.setLayoutManager(verticalLayoutManager);

        initSwipe();

    }

    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT){
                    // adapter.removeItem(position);
                    rview.setAdapter(vadapter);

                    Toast.makeText(MainActivity.this, "LEFT"+fruitslist.get(position), Toast.LENGTH_SHORT).show();
                } else {
                    rview.setAdapter(vadapter);

                    Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();

//                    removeView();
//                    edit_position = position;
//                    alertDialog.setTitle("Edit Country");
//                    et_country.setText(countries.get(position));
//                    alertDialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX > 0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                       // c.drawBitmap(icon,null,icon_dest,p);

                        //triggerRecyclerview();

                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        // c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rview);
    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {
        private List<String>namelist;

        public VerticalAdapter(ArrayList<String> fruitslist) {
            this.namelist=fruitslist;
        }

        @NonNull
        @Override
        public VerticalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.secondxml, viewGroup, false);
            return new MyViewHolder(item);
        }

        @Override
        public void onBindViewHolder(@NonNull VerticalAdapter.MyViewHolder myViewHolder, int i) {

            myViewHolder.tview.setText(fruitslist.get(i));
            myViewHolder.image.setImageResource(imglist[i]);

        }

        @Override
        public int getItemCount() {
            return fruitslist.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
          public ImageView image;
          public TextView tview;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                image=itemView.findViewById(R.id.images);
                tview=itemView.findViewById(R.id.fruit);
            }
        }
    }
}
