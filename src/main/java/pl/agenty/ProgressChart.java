package pl.agenty;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.uma.jmetal.measure.PullMeasure;

import java.util.Timer;
import java.util.TimerTask;


public class ProgressChart extends ApplicationFrame{
    private final long maxTime;
    private final String title;
    private XYSeriesCollection dataset;
    private long interval = 100;

    public ProgressChart(String title, long maxTime) {
        super( title );
        this.title = title;
        this.maxTime = maxTime;
        this.dataset = new XYSeriesCollection();
    }

    public void drawChart(){
        final JFreeChart chart = createChart( this.dataset );
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 370 ) );
        chartPanel.setMouseZoomable( true , false );
        setContentPane( chartPanel );

    }

    public void initSerie(String title, final PullMeasure measure) {
        final XYSeries serie = new XYSeries(title);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            private long time = interval;
            @Override
            public void run() {
                serie.add(time, ((Double)measure.get()).doubleValue());
                time += interval;

                if(time > maxTime) {
                    this.cancel();
                }
            }
        }, interval, interval);

        dataset.addSeries(serie);
    }

    private JFreeChart createChart( final XYDataset dataset ) {
        return ChartFactory.createTimeSeriesChart(
                title,
                "Time",
                "Objective",
                dataset,
                true,
                false,
                false);
    }
}