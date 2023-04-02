package com.example.trackit;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Stats_frag extends Fragment {

    public Stats_frag() {
        // Required empty public constructor
    }
    BarChart barchart;
    LineChart lineChart;
    PieChart piechart;
    TextView balance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Bar graph
        View view=inflater.inflate(R.layout.fragment_stats_frag, container, false);
        String mobno = getArguments().getString("mobno");
        barchart=view.findViewById(R.id.barchart);
        balance=view.findViewById(R.id.balance);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://track-it-a092e-default-rtdb.firebaseio.com/");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                balance.setText("Balance:₹"+snapshot.child("users").child(mobno).child("balance").getValue(String.class));

                //Linechart start
                int x=(int)snapshot.child("users").child(mobno).child("expense").getChildrenCount();
                lineChart=view.findViewById(R.id.linechart);
                ArrayList<Entry> savings=new ArrayList<>();
                Entry entry=new Entry(0,0);
                savings.add(entry);
                for(int i=1;i<=x;i++){
                    int y=Integer.parseInt(snapshot.child("users").child(mobno).child("expense").child(String.valueOf(i)).child("cbalance").getValue(String.class));
                    Entry entry1=new Entry(i,y);
                    savings.add(entry1);
                }
                LineDataSet lineDataSet=new LineDataSet(savings,"SAVINGS");
                lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                ArrayList<ILineDataSet> datasets=new ArrayList<>();
                datasets.add(lineDataSet);
                LineData data=new LineData(datasets);
                lineChart.setData(data);
                lineChart.animateX(3000);
                lineChart.animateY(3000);
                lineChart.getDescription().setText("");
                lineChart.invalidate();
                //Linechart end

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayList<BarEntry> income=new ArrayList<>();
        ArrayList<BarEntry> expense=new ArrayList<>();
        for(int i=1;i<=12;i++){
            float value=(float)(i%3);
            float value1=(float)(i%5);
            BarEntry barEntry=new BarEntry(2*i-1,value);
            BarEntry barEntry1=new BarEntry(2*i,value1);
            income.add(barEntry);
            expense.add(barEntry1);
        }
        BarDataSet barDataSet=new BarDataSet(income,"INCOME");
        barDataSet.setColors(Color.GREEN);
        barDataSet.setDrawValues(true);

        BarDataSet barDataSet1=new BarDataSet(expense,"EXPENSE");
        barDataSet1.setColors(Color.RED);
        barDataSet1.setDrawValues(true);

        BarData bardata=new BarData();
        bardata.addDataSet(barDataSet);
        bardata.addDataSet(barDataSet1);
        barchart.setData(bardata);
        barchart.animateY(5000);
        barchart.getDescription().setText("EXPENDITURE CHART");
        barchart.getDescription().setTextColor(Color.BLUE);

        //Bar graph finish


        //Line chart
//        lineChart=view.findViewById(R.id.linechart);
//        ArrayList<Entry> savings=new ArrayList<>();
//        for(int i=1;i<=12;i++){
//            float value=(float)(i%3);
//            float value1=(float)(i%5.0);
//            Entry entry=new Entry(i,value-value1);
//            savings.add(entry);
//        }
//        LineDataSet lineDataSet=new LineDataSet(savings,"SAVINGS");
//        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        ArrayList<ILineDataSet> datasets=new ArrayList<>();
//        datasets.add(lineDataSet);
//        LineData data=new LineData(datasets);
//        lineChart.setData(data);
//        lineChart.animateX(5000);
//        lineChart.animateY(5000);
//        lineChart.getDescription().setText("");
//        lineChart.invalidate();
        //Line chart finished

        //Pie chart start
        piechart=view.findViewById(R.id.piechart);
        ArrayList<PieEntry> monthly=new ArrayList<>();
        for(int i=1;i<=12;i++){
            PieEntry entry=new PieEntry((float)(2*i),"");
            monthly.add(entry);
        }
        PieDataSet pieDataSet=new PieDataSet(monthly,"Expenditure");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setDrawValues(true);
        PieData pieData=new PieData(pieDataSet);
        piechart.setData(pieData);
        piechart.animateY(5000);
        piechart.getDescription().setText("");
        piechart.invalidate();
        //Pie chart finished
        return view;
    }
}