# Alpaca Core Local SDK Java Wrapper

Since we want to be good open source citizens, we conform to Java and gradle's ridiculous directory structure conventions.

* `jni.hpp` - our fork of [mapbox/jni.hpp](https://github.com/mapbox/jni.hpp): a third party library which wraps JNI for C++
* `ac-jni` - JNI code which produces the native shared lib for the wrapper
* `src` - com.alpacacore Java library and tests (in many many subdirs)
* `test` - internal tests: testing the JNI code itself

## Build

The Java wrapper should work with Java 8+, but Java 17+ is needed to build the Java tests and examples

This repo provides a dual build structure and thus it's somewhat of a mess (lowest common denominator)

### CMake (ac-build)

First, it can be configured and built with CMake alone from the root `CMakeLists.txt`. This build, including generating jars and running java tests, is driven entirely through CMake. Tests can be executed through CTest. This build supports ac-build flavors.

### Android Gradle

Second, it can be built by gradle as an Android project. This build is driven through gradle with this directory as root. It runs CMake internally skipping the build of jars and tests.

The internal CMake configuration does not start with the root `CMakeLists.txt`, but instead with `src/native/CMakeLists.txt`. Thus it skips a lot of subdirs and targets: examples, tools, tests, etc.

Here jars and tests (JUnit- and Android-only) are built through gradle. C++-only tests and the internal Java Wrapper tests are not built and run.

## Development environment

Here's the recommended way to setup a development environment for building this project:

First, cover the basic setup from [ac-local Development environment doc](https://github.com/alpaca-core/ac-local/blob/master/doc/dev/dev-env.md)

Aditionally:

* Ubuntu:
    * `$ sudo apt install openjdk-17-jdk`
* Manjaro:
    * `$ sudo pacman -S jdk-openjdk`
* Windows:
    * JDK. [Microsoft OpenJDK 17](https://learn.microsoft.com/en-us/java/openjdk/download#openjdk-17) works, but any other JDK 17 or later should be fine as well
    * Set the `JAVA_HOME` environment variable to the JDK installation directory
* macOS:
    * `$ brew install openjdk` or `$ brew install openjdk@17` (both should work)
    * Set the `JAVA_HOME` environment variable to the JDK installation directory, but note that CMake doesn't deal well with the brew symlinks for openjdk.
    * Brew will symlink the installation to `/opt/homebrew/opt/openjdk`. *Do not* set this to `JAVA_HOME`. Instead, set it to the actual installation directory, which will likely be `/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home`
