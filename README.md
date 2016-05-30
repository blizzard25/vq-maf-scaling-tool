# vq-maf-scaling-tool
Simple tool for simplification of MAF (Mass Air Flow) sensor voltage scaling for Nissan and Infinitis on the VQ platform. 

External Libraries (all libraries can be found in the .zip file):

Apache Commons CSV 1.2 

Apache Commons Lang 3.4 

jcommon 1.0.23

jfreechart 1.0.19

Entry point is src/mafscalermain/Main.java. This isn't what I would call the cleanest code, there's a few features I started on but haven't completed since this project is currently on hold. Feel free to edit or suggest changes.

To use: If you just want to run the program, download the executable .jar file and run it. Upload your Uprev ROM Editor or Uprev Cipher .CSV log file. Required log parameters for the current version are MAF Voltage B1 and B2, Fuel Corrections B1 and B2, A/F Ratio B1 and B2, Accelerator Pedal 1 Position Voltage, Engine Speed, and Base Fuel Schedule (more headers will be required for future versions with updated features). The graph displays the logged data points, while the table displays the MAF voltage axis value and the suggested MAF multiplier based on the input log (NaN means there were no input values at that specific voltage to evaluate).

If you would like to create a project, I included all of the required external libraries in a .zip file.

Known bug 1: Double headers aren't supported yet, so if an upload doesn't display the chart and multiplier the most common bug lies in the log generating a duplicate header (so far the most common culprit is duplicate tracer headers when using ROM Editor, however some cars log duplicate Knock Strength, Engine Speed, and Base Fuel Schedule. So be aware). 

Known bug 2: Display chart/table panel centers vertically fine, however it is offset slightly horizontally.

Future versions: Implement a regression function rather than just using a percent average. Allow an input injector scaler to serve as a dependent variable in the calculations. Add support for uploading ignition timing and knock strength logs, output suggested target AFR and ignition timing values from the input data using MTBT (Minimum timing, best torque)
