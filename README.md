# vq-maf-scaling-tool
Simple tool for simplification of MAF scaling for Nissan and Infinitis on the VQ platform. 

External Libraries:
Apache Commons CSV 1.2 
Apache Commons Lang 3.4 
jcommon 1.0.23
jfreechart 1.0.19

Entry point is src/mafscalermain/Main.java. This isn't what I would call the cleanest code, there's a few features I started on but haven't completed since this project is currently on hold. Feel free to edit or suggest changes.

To use: If you just want to run the program, download the executable .jar file and run it. Upload your Uprev ROM Editor or Uprev Cipher .CSV log file. Required log parameters for the current version are MAF Voltage B1 and B2, Fuel Corrections B1 and B2, Engine Speed, and Base Fuel Schedule (more headers will be required for future versions with updated features). The graph displays the logged data points, while the table displays the MAF voltage axis value and the suggested MAF multiplier based on the input log.

Known bugs: Double headers aren't supported yet, so if an upload doesn't display the chart and multiplier the most common bug lies in the log generating a duplicate header (so far the most common culprit is duplicate tracer headers when using ROM Editor, however some cars log duplicate Knock Strength, Engine Speed, and Base Fuel Schedule. So be aware)
