package com.example.micrecord;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;


public class RtChartsActivity {
	private GraphicalView view;
	private TimeSeries dataset = new TimeSeries("Mic Data");
	private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	private XYSeriesRenderer renderer = new XYSeriesRenderer();
	private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	public RtChartsActivity() {
		mDataset.addSeries(dataset);
		renderer.setColor(Color.BLUE);
		renderer.setPointStyle(PointStyle.SQUARE);
		renderer.setFillPoints(true);
		renderer.setLineWidth(7);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setXTitle("Time");
		mRenderer.setYTitle("Value");
		mRenderer.setLabelsTextSize(20);
		mRenderer.setPanEnabled(true,true);
		mRenderer.addSeriesRenderer(renderer);
	}
	
	public GraphicalView getView(Context context) {
		view = ChartFactory.getLineChartView(context, mDataset, mRenderer);
		return view;
		
	} 
	public void addNewPoints (Point p) {
		dataset.add(p.getX(), p.getY());
	}

}
