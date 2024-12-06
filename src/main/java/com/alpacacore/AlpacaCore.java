// Copyright (c) Alpaca Core
// SPDX-License-Identifier: MIT
//
package com.alpacacore;

/// @defgroup java-local Java Local API
/// Java API for local infernence.

/// SDK Instance.
/// An entry point to using the SDK.
/// @ingroup java-local
public class AlpacaCore {
    static {
        System.loadLibrary("ac-jni");
    }

    /// Load a plugin from a specific file ignoring search paths.
    public static native void loadPlugin(String path);

    /// Add a directory to the plugin search path.
    public static native void addPluginDir(String dir);

    /// Add directories to the plugin search path from an environment variable.
    /// The separator is platform dependent: ':' on Unix, ';' on Windows.
    public static native void addPluginDirsFromEnv(String envVar);

    /// Load all plugins from the search path.
    public static native void loadAllPlugins();

    /// Load a model from an asset description.
    /// The progress callback is optional.
    public static native Model loadModel(ModelAssetDesc desc, Object params, ProgressCallback cb);

    /// Release a model.
    /// Models are managed manually not releasing a model is considered a leak.
    public static native void releaseModel(Model model);

    /// Release a model instance.
    /// Instances are managed manually not releasing a instance is considered a leak.
    public static native void releaseInstance(Instance instance);
}
