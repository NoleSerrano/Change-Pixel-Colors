# Change-Pixel-Colors
Changes all the pixel colors for a set of images

## Parameters
The following are variables that should be set programmatically:
* __outputFolderPath__ is the output folder path in the current working directory which by default is set to "output".
* __inputFolderPath__ is the input folder path in the current working directory which by default is set to "input" and will house the PNG image files you want to modify.
* __imagePrefixName__ is the an optional string that will be applied to the base image names as a prefix.

## GUI
Here is what the GUI will look like (Pretty bad):
![image](https://github.com/NoleSerrano/Change-Pixel-Colors/assets/43283288/66e1d78e-454f-4377-be8a-5de89ad2d4ae)

The colors on the right are selectable. Upon selection, you can change the color using the color adjustments.
Hitting "Apply Changes" will apply the colors on the right to the image and update the existing colors which are on the left.
Hitting "Save Changes" will use the new colors that you've selected and create a new set of images with the imagePrefixName as a prefix and will save
those images to the output folder. The code works with transparent images.

## Future
* Want the image to update automatically after changing the color at all
* Want the hue adjustment box to actually correspond to the slider or better have the box actually be the slider
* Want a color selection tool
