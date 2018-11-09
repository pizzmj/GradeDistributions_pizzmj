/**
 * Michael Pizzo
 * R01680467
 */

package com.example.mike.gradedistributions_pizzmj;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
 //Binding views with ButterKnife
    @BindView(R.id.et1) EditText textField1;
    @BindView(R.id.et2) EditText textField2;
    @BindView(R.id.et3) EditText textField3;
    @BindView(R.id.et4) EditText textField4;
    @BindView(R.id.et5) EditText textField5;
    @BindView(R.id.et6) EditText textField6;
    @BindView(R.id.btn_compute) Button btn;
    @BindView(R.id.layout) ViewFlipper flipper;
    @BindView(R.id.bar_graph) GraphView graph;

    int totalStudents, aStudents, bStudents, cStudents, dStudents, fStudents;
    int percentA, percentB, percentC, percentD, percentF;

    String dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public void compute(View view) {
        //parses integers from EditTexts
        totalStudents = Integer.parseInt(textField1.getText().toString());
        aStudents = Integer.parseInt(textField2.getText().toString());
        bStudents = Integer.parseInt(textField3.getText().toString());
        cStudents = Integer.parseInt(textField4.getText().toString());
        dStudents = Integer.parseInt(textField5.getText().toString());
        fStudents = Integer.parseInt(textField6.getText().toString());

        //Calculates percentages
        percentA = (aStudents * 100) / totalStudents;
        percentB = (bStudents * 100) / totalStudents;
        percentC = (cStudents * 100) / totalStudents;
        percentD = (dStudents * 100) / totalStudents;
        percentF = (fStudents * 100) / totalStudents;

        //Sets the text that will show up in the Dialog box
        dialogText = "The percentages of grade distribution are: \n" +
                "As: " + percentA + "%\n" +
                "Bs: " + percentB + "%\n" +
                "Cs: " + percentC + "%\n" +
                "Ds: " + percentD + "%\n" +
                "Fs: " + percentF + "%\n" +
                "Continue to graph?";

        //Creates the dialog box and adds buttons and text to it
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogText);
        builder.setPositiveButton(R.string.dialogYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //changes views from original view to the graph
                flipper.showNext();
            }
        });
        builder. setNegativeButton(R.string.dialogNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //closes dialog box
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        //sets data for the bar graph
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
           new DataPoint(0, percentA),
           new DataPoint(1, percentB),
           new DataPoint(2, percentC),
           new DataPoint(3, percentD),
           new DataPoint(4, percentF)
        });
        graph.addSeries(series);
        graph.setTitle("Percentage of Student Grades");

        //Sets colors of the bar graph
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        //sets the space in between the bars of the graph, also draws the values above each bar and sets the color of the value
        series.setSpacing(50);
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        //Labels the horizontal axis of the graph
        StaticLabelsFormatter formatter = new StaticLabelsFormatter(graph);
        formatter.setHorizontalLabels(new String[] {"As","Bs","Cs","Ds","Fs"});
        graph.getGridLabelRenderer().setLabelFormatter(formatter);
    }
}






