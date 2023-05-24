# Change-Pixel-Colors
Changes all the pixel colors for a set of images

## Parameters
The following are variables that should be set programmatically:
* __outputFolderPath__ is the output folder path in the current working directory which by default is set to "output".
* __inputFolderPath__ is the input folder path in the current working directory which by default is set to "input" and will house the PNG image files you want to modify.
* __imagePrefixName__ is the an optional string that will be applied to the base image names as a prefix.

## GUI
Here is what the GUI will look like:
![image](https://github.com/NoleSerrano/Change-Pixel-Colors/assets/43283288/2258f844-ec17-4c92-9fec-93b288a0dbda)

Select a color from the selection panel. Upon selection, you can change the color using the color chooser panel. Hitting "Save Changes" will use the new colors that you've selected
and create a new set of images with the imagePrefixName as a prefix and will save those images to the output folder. The code works with transparent images.

## Future
* Want undo button
* Color picker tool
