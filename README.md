# vq-maf-scaling-tool
Simple tool for simplification of MAF scaling on the VQ platform. Requires addition of Apache Commons CSV 1.2 Apache Commons Lang 3.4, jcommon 1.0.23, and jfreechart 1.0.19 external libraries.

Entry point is src/mafscalermain/Main.java. This isn't what I would call the cleanest code, there's a few features I started on but haven't completed since this project is currently on hold. Feel free to edit or suggest changes.

To use: Currently only requires the MAF Voltage B1 and B2, Fuel Corrections B1 and B2, Engine Speed, Base Fuel Schedule to be logged. There is more to come. Double headers aren't supported yet, so if an upload doesn't display the chart and multiplier the most common bug lies in the log generating a duplicate header (so far the most common culprit is duplicate tracer headers when using ROM Editor, however some cars log duplicate Knock Strength, Engine Speed, and Base Fuel Schedule. So be aware)
