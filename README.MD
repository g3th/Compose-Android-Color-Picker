# Compose Android Color Picker
## A Color picker module written in Kotlin using Compose UI Framework

https://github.com/user-attachments/assets/07a9bcd3-4a6e-4a35-b7ed-6f3e20f8a741


Although plenty of colour picker projects exist on the web, easily found by searching sites like 'Medium',  'StackOverfow' or similar pulp-level publications, it is difficult to find a module written in 'pure' Compose, without importing things like 'android.graphics.Canvas' or 'android.graphics.Paint'.


This module picks a colour of your choice by placing a simple bitmap with the ```drawBehind``` function in a ```BoxWithConstraints```, and subsequently implementing the lower-level detector function ```PointerInputScope.detectDragGestures```.


Information on original bitmap and resized bitmap is included in the UI, and a call to ```thecolorapi.com``` will convert the RGB value into a human-readable colour name.	


Features:

- Box with colour picker cursor
- Original Bitmap size, and Resized bitmap information
- ```+``` and ```-``` buttons to resize bitmap to the user's liking
- Color Api endpoint call, and serialized json data using kotlin-stdlib ```kotlin.text``` for simple regex parsing.
- Human Readable Colours from RGB values
- Horrible color scheme layout to annoy people who are particularly bothered

TODO:

- Alpha value bar
- Brightness
