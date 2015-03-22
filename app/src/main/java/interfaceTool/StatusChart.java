package interfaceTool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.plot.XYPlot;
import org.afree.data.general.DefaultPieDataset;
import org.afree.data.time.TimeSeries;
import org.afree.data.time.TimeSeriesCollection;
import org.afree.data.xy.XYDataset;
import org.afree.graphics.geom.RectShape;

import java.util.Vector;

/**
 * Created by LT on 2015/3/23.
 *
 */
public class StatusChart {
    private Bitmap bitmap;
    private RectShape rectArea;
    private Canvas canvas;
    private AFreeChart chart;

    public StatusChart() {
        intChart();
    }

    private void intChart() {
        //Setting different width and height based on the orientation.
        bitmap = Bitmap.createBitmap(600, 400, Bitmap.Config.ARGB_8888);
        rectArea = new RectShape(0.0, 0.0, 600, 400);
    }

    public void drawChart(AFreeChart chart) {
        canvas = new Canvas(bitmap);
        this.chart = chart;
        this.chart.draw(canvas, rectArea);

        turn();
    }

    private void turn() {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap ans = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap=ans;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    XYDataset data;
    XYPlot xyplot;

    public void drawChart(String title,String x,String y,Vector<TimeSeries> value){
        data=new TimeSeriesCollection();
        chart = ChartFactory.createTimeSeriesChart(title, x, y, data, true, true, true);
        XYPlot xyplot;
        xyplot = (XYPlot) chart.getPlot();

        for (int i=0;i<value.size();i++){
            ((TimeSeriesCollection) this.data).addSeries(value.get(i));
        }
        drawChart(chart);
    }
}
