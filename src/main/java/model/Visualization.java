package model;

import javafx.scene.chart.PieChart;

public class Visualization {

  public static PieChart createTaskPieChart(int inProgress, int done) {
    PieChart.Data inProgressData = new PieChart.Data("In Progress", inProgress);
    PieChart.Data doneData = new PieChart.Data("Done", done);

    PieChart pieChart = new PieChart();
    pieChart.getData().addAll(inProgressData, doneData);
    return pieChart;
  }
}
