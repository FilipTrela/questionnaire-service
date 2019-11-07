
var dataPointsElements = [];

Object.keys(statistic).forEach(function(key) {
    // console.log(key, statistic[key]);
    dataPointsElements.push({y: statistic[key], label: key});
});

window.onload = function() {

    var chart = new CanvasJS.Chart("chartContainer", {
        theme: "dark2", // "light1", "light2", "dark1", "dark2"
        exportEnabled: true,
        animationEnabled: true,
        title: {
            text: "Question: "+question
        },
        data: [{
            type: "pie",
            startAngle: 25,
            toolTipContent: "<b>{label}</b>: {y}",
            showInLegend: "true",
            legendText: "{label}",
            indexLabelFontSize: 20,
            indexLabel: "{label} - {y}",
            dataPoints: dataPointsElements
        }]
    });
    chart.render();

}

