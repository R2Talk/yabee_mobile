package br.com.ca.asap.graph;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import br.com.ca.shareview.R;

/**
 * InitiativesChart
 *
 */
public class InitiativesChart {

    private static int[] COLORS = new int[] { Color.GREEN, Color.RED};

    public View getInitiativesBarChart(Integer[] greenValues, Integer[] redValues, String[] initiativeNames, Context context) {

        CategorySeries greenSeries = new CategorySeries(context.getResources().getString(R.string.onTime));
        CategorySeries redSeries = new CategorySeries(context.getResources().getString(R.string.late));

        //set green series
        for (int i = 0; i < greenValues.length; i++) {
            greenSeries.add(initiativeNames[i], greenValues[i]);
        }
        //set red series
        for (int i = 0; i < redValues.length; i++) {
            redSeries.add(initiativeNames[i] + i, redValues[i]);
        }

        //prepare multiple XY data set
        XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
        dataSet.addSeries(greenSeries.toXYSeries());
        dataSet.addSeries(redSeries.toXYSeries());


        //prepare green renderer
        XYSeriesRenderer greenRenderer = new XYSeriesRenderer();
        greenRenderer.setDisplayChartValues(true);
        greenRenderer.setChartValuesSpacing((float) 1);
        greenRenderer.setColor(Color.GREEN);
        greenRenderer.setChartValuesTextSize(20);

        //prepare red renderer
        XYSeriesRenderer redRenderer = new XYSeriesRenderer();
        redRenderer.setDisplayChartValues(true);
        redRenderer.setChartValuesSpacing((float) 1);
        redRenderer.setColor(Color.RED);
        greenRenderer.setChartValuesTextSize(20);

        //prepare multiple series renderer
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
        mRenderer.addSeriesRenderer(greenRenderer);
        mRenderer.addSeriesRenderer(redRenderer);

        //mRenderer attributes
        mRenderer.setChartTitle(context.getResources().getString(R.string.initiativesText));

        mRenderer.setXLabelsPadding(30);
        mRenderer.setLegendTextSize(10);

        mRenderer.setXTitle(context.getResources().getString(R.string.initiativesText));
        mRenderer.setYTitle(context.getResources().getString(R.string.deliverablesText));

        //set multiple renderer X Text
        for (int i = 0; i < initiativeNames.length; i++) {
            mRenderer.addXTextLabel(i+1,initiativeNames[i]);
        }

        /*
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.argb(100, 50, 50, 50));
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(15);
        mRenderer.setLegendTextSize(15);
        mRenderer.setMargins(new int[]{20, 30, 15, 0});
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setStartAngle(90);
        */

        //view from Chart Factory
        View initiativesGraphView = ChartFactory.getBarChartView(context, dataSet, mRenderer, BarChart.Type.DEFAULT);

        return initiativesGraphView;
    }
}
