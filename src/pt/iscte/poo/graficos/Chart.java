package pt.iscte.poo.graficos;

/* 
 * Chart
 * @author POO2015
 * 
 * ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 * -------------------
 * This class is an adaptation of LineChartDemo6.java
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 * -------------------
 * 
 */

import java.awt.Color;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import pt.iscte.poo.instalacao.Relogio;

/**
 * 
 * @author POO2015
 *  
 * The {@link Chart} class is adapted from LineChartDemo6.java  (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * The Chart class provides a simple interface for a simple line graph with multiple {@link XYSeries}.
 *
 */

/**
 * @author lmmn
 *
 */
@SuppressWarnings("serial")
public class Chart extends ApplicationFrame implements Observer {

	// private List<XYSeries> allSeries = new LinkedList<XYSeries>();
	private XYDataset dataset;
	private final JFreeChart chart;
	private final ChartPanel chartPanel;

	/**
	 * Creates a Chart
	 *
	 * @param title
	 *            the chart (and frame) title.
	 */
	public Chart(final String title) {
		super(title);

		dataset = new XYSeriesCollection();
		chart = createChart(dataset, title);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);
		pack();
		RefineryUtilities.centerFrameOnScreen(this);

	}

	private JFreeChart createChart(final XYDataset dataset, String title) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createXYLineChart(title, // chart
																		// title
				"Tempo", // x axis label
				"Potencia (W)", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);

		// final JFreeChart chart = ChartFactory.createScatterPlot(title, //
		// chart
		// // title
		// "X", // x axis label
		// "Y", // y axis label
		// dataset, // data
		// PlotOrientation.VERTICAL, true, // include legend
		// true, // tooltips
		// false // urls
		// );

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);

		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);

		// get a reference to the plot for further customisation...
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		// plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);

		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		// renderer.setSeriesLinesVisible(0, true);
		// renderer.setSeriesShapesVisible(0, false);
		// renderer.setSeriesLinesVisible(1, true);
		// renderer.setSeriesShapesVisible(1, false);
		// renderer.setSeriesLinesVisible(2, true);
		// renderer.setSeriesShapesVisible(2, false);
		plot.setRenderer(renderer);

		// change the auto tick unit selection to integer units only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}

	/**
	 * 
	 * The setPoint function adds a new point (x,y) to a given line (series)
	 * 
	 * @param name
	 *            The name of the series this point will be added to. If a
	 *            series with the given name does not exist, one will be
	 *            created. More than 5 series may clutter the chart.
	 * @param x
	 *            The abscissa value for this point
	 * @param y
	 *            The ordinate value for this point
	 */

	private void setPoint(String name, int x, double y) {
//		System.out.println(name + " t " + x + " - " + y);
		XYSeries series = null;
		try {
			series = ((XYSeriesCollection) dataset).getSeries(name);
		} catch (UnknownKeyException e) {
			series = new XYSeries(name);
			((XYSeriesCollection) dataset).addSeries(series);
		}
		series.add(x, y);
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) chart
				.getXYPlot().getRenderer();
		renderer.setSeriesLinesVisible(series.getItemCount() - 1, true);
		renderer.setSeriesShapesVisible(series.getItemCount() - 1, false);

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		@SuppressWarnings("unchecked")
		Map<String, Double> potencias = (Map<String, Double>) arg1;
		for (String line : potencias.keySet()) {
			try {
				XYSeries series = ((XYSeriesCollection) dataset)
						.getSeries(line);
				// int lastX = series.getX(series.getItemCount() -1
				// ).intValue();
				double lastY = series.getY(series.getItemCount() - 1)
						.doubleValue();
				setPoint(line,
						Relogio.getInstanciaUnica().getTempoAtual() - 1,
						lastY);
			} catch (UnknownKeyException e) {
			}
			setPoint(line, Relogio.getInstanciaUnica().getTempoAtual(),
					potencias.get(line));
		}

	}

}
