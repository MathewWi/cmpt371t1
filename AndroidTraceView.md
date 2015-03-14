# Introduction #

Traceview allows us to dump a profiled run of the application and view it in a meaningful manner.


# Details #

> Despite the urge to use traceview via code it proved to be unworthy.  You can start the profiler easily enough from the adb shell.

```
(start luminance app on phone)

# adb shell

## am profile ca.sandstorm.luminance start /sdcard/luminance.trace

(wait)

(play the game)

## am profile ca.sandstorm.luminance stop /sdcard/luminance.trace

## exit

# adb pull /sdcard/luminance.trace
# traceview /full_path_to/luminance.trace

(yes it has to be the full path)
```