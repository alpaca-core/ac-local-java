# Copyright (c) Alpaca Core
# SPDX-License-Identifier: MIT
#

# find_package(JNI) does not work on Android with CMake < 2.24
cmake_minimum_required(VERSION 3.24 FATAL_ERROR)

project(ac-local-java
    VERSION 1.0.0
    DESCRIPTION "Java wrapper of the Alpaca Core Local SDK"
    LANGUAGES CXX # not adding Java here, as jar builds are optional
)

include(./get-ac-build.cmake)

if(sanitizerFlags)
    # TODO: #134
    message(WARNING "${CMAKE_PROJECT_NAME} is configured with '${sanitizerFlags}'. Java will likely fail to load the JNI library")
endif()

include(init_ac_prj)

#################
# cmake lib
include(ac_build_prj_util)

function(add_ac_java_module name)
    add_library(${name} MODULE ${ARGN})
    if(NOT WIN32)
        target_compile_options(${name} PRIVATE
            -fvisibility=hidden
            -fvisibility-inlines-hidden
        )
    endif()
    if(APPLE)
    	# by default on macOS the default suffix by cmake for modules is .so
    	# this doesn't play well with many loaders which require .dylib
    	# instead of changing this globally and potentially breaking third
    	# party code, we'll only do this for our own modules
    	set_target_properties(${name} PROPERTIES SUFFIX ".dylib")
    endif()
endfunction()


#################
# options

option(AC_LOCAL_JAVA_BUILD_TESTS "${PROJECT_NAME}: build tests" ${testsDefault})
option(AC_LOCAL_JAVA_BUILD_EXAMPLES "${PROJECT_NAME}: build examples" ${examplesDefault})
mark_as_advanced(AC_LOCAL_JAVA_BUILD_TESTS AC_LOCAL_JAVA_BUILD_EXAMPLES)

#######################################
# packages

add_ac_local(0.1.1)
find_package(JNI REQUIRED)
CPMAddPackage(gh:mapbox/jni.hpp@4.0.0)
if(jni.hpp_ADDED)
    add_library(mapbox-jni.hpp INTERFACE)
    add_library(mapbox::jni.hpp ALIAS mapbox-jni.hpp)
    target_include_directories(mapbox-jni.hpp INTERFACE "${jni.hpp_SOURCE_DIR}/include")
endif()

#######################################
# subdirs

add_subdirectory(ac-jni)

# only buld java with CMake if we are the top level project
# otherwise we're a subproject (likely of a gradle build and the java build is handled there)
if(PROJECT_IS_TOP_LEVEL)
    find_package(Java REQUIRED)
    include(UseJava)

    add_subdirectory(src)

    if(AC_LOCAL_JAVA_BUILD_TESTS OR AC_LOCAL_JAVA_BUILD_EXAMPLES)
        if(Java_VERSION_MAJOR LESS 17)
            message(SEND_ERROR "${CMAKE_PROJECT_NAME}: Java 17+ is required to build Java tests and examples")
        endif()
    endif()

#~     if(AC_LOCAL_JAVA_BUILD_TESTS)
#~         enable_testing()
#~         add_subdirectory(test)
#~     endif

#~     if(AC_LOCAL_JAVA_BUILD_EXAMPLES)
#~         add_subdirectory(example)
#~     endif()
endif()
