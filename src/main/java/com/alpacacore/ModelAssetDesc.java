// Copyright (c) Alpaca Core
// SPDX-License-Identifier: MIT
//
package com.alpacacore;

/// An descriptor for model assets.
/// @ingroup java-local
public class ModelAssetDesc {
    /// Default constructor for an empty description.
    public ModelAssetDesc() {}

    /// Constructor from components.
    public ModelAssetDesc(String type, AssetInfo[] assets, String name) {
        this.type = type;
        this.assets = assets;
        this.name = name;
    }

    /// Asset (weights) type.
    public String type;

    /// Information for an individual asset.
    public static final class AssetInfo {
        public AssetInfo(String path, String tag) {
            this.path = path;
            this.tag = tag;
        }
        public String path; ///< Path to the asset.
        public String tag; ///< Asset tag.
    }
    public AssetInfo[] assets; ///< Model assets.

    /// Name tag.
    /// This field is not used by the library in any way besides logs and may be helpful for debugging.
    public String name;
}
