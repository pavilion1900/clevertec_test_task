package ru.clevertec.task.perfomance;

public class CheckPerformance<T> {
    private Performance<T> performanceFirst;
    private Performance<T> performanceSecond;

    public CheckPerformance(Performance<T> performanceFirst, Performance<T> performanceSecond) {
        this.performanceFirst = performanceFirst;
        this.performanceSecond = performanceSecond;
    }

    public void showInfo() {
        String ln = System.lineSeparator();
        System.out.printf("%-4s %-28d %10s %10s %1s",
                "N = ", performanceFirst.getAmountElements(),
                performanceFirst.getFactory().getName(),
                performanceSecond.getFactory().getName(), ln);
        System.out.printf("%44s %10s %1s", "ms", "ms", ln);
        String format = "%-33s %10d %10d %1s";
        System.out.printf(format, "Add N elements to the end",
                performanceFirst.addNElementsToEnd(),
                performanceSecond.addNElementsToEnd(), ln);
        System.out.printf(format, "Add N elements to the head",
                performanceFirst.addNElementsToHead(),
                performanceSecond.addNElementsToHead(), ln);
        System.out.printf(format, "Add N elements to the middle",
                performanceFirst.addNElementsToMiddle(),
                performanceSecond.addNElementsToMiddle(), ln);
        System.out.printf(format, "Get the last element N times",
                performanceFirst.getLastElementNTimes(),
                performanceSecond.getLastElementNTimes(), ln);
        System.out.printf(format, "Get the first element N times",
                performanceFirst.getFirstElementNTimes(),
                performanceSecond.getFirstElementNTimes(), ln);
        System.out.printf(format, "Get the middle element N times",
                performanceFirst.getMiddleElementNTimes(),
                performanceSecond.getMiddleElementNTimes(), ln);
        System.out.printf(format, "Remove the last element N times",
                performanceFirst.removeLastElementNTimes(),
                performanceSecond.removeLastElementNTimes(), ln);
        System.out.printf(format, "Remove the first element N times",
                performanceFirst.removeFirstElementNTimes(),
                performanceSecond.removeFirstElementNTimes(), ln);
        System.out.printf(format, "Remove the middle element N times",
                performanceFirst.removeMiddleElementNTimes(),
                performanceSecond.removeMiddleElementNTimes(), ln);
    }

    public static void main(String[] args) {
        Performance<String> performArrayList =
                new Performance<>(new ArrayListFactory<>(), 100_000, "Hello");
        Performance<String> performLinkedList =
                new Performance<>(new LinkedListFactory<>(), 100_000, "Hello");
        CheckPerformance<String> check =
                new CheckPerformance<>(performArrayList, performLinkedList);
        check.showInfo();
    }
}
