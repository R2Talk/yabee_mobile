package br.com.ca.asap.graph;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * DeliverablesChart
 *
 * Prepares and return a View object with chart image using the ACHARTENGINE libray.
 *
 * The Pie Chart represents the state of the deliverables.
 *
 */
public class DeliverablesChart {

    private static int[] COLORS = new int[] { Color.GREEN, Color.RED};


    /**
     * getDeliverablesPieChart
     *
     * Receives arrays of values and names for pie chart preparation.
     * Also receives the context of the application thats is required by the chart view factory.
     *
     * @param values
     * @param names
     * @param context
     * @return
     */
    public View getDeliverablesPieChart(double[] values, String[] names, Context context) {

        CategorySeries mSeries = new CategorySeries("");
        DefaultRenderer mRenderer = new DefaultRenderer();
        GraphicalView mChartView = null;

        //renderer
        //mRenderer.setApplyBackgroundColor(true);
        //mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setLegendTextSize(20);
        mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
        //mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);

        //data set
        for (int i = 0; i < values.length; i++) {
            mSeries.add(names[i] + " " + values[i], values[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);
        }

        //view
        return ChartFactory.getPieChartView(context, mSeries, mRenderer);

    }
}
