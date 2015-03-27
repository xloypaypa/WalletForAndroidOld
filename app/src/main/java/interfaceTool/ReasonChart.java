package interfaceTool;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.data.general.DefaultPieDataset;
import org.afree.graphics.geom.RectShape;

/**
 * Created by LT on 2015/3/22.
 *
 */
public class ReasonChart {
    private Bitmap bitmap;
    private RectShape rectArea;
    private Canvas canvas;
    private AFreeChart chart;

    public ReasonChart() {
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
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void drawChart(DefaultPieDataset data,String title){
        chart = ChartFactory.createPieChart(title,data,true,true,true);
        drawChart(chart);
    }
}