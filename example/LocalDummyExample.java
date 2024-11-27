// Copyright (c) Alpaca Core
// SPDX-License-Identifier: MIT
//
package com.alpacacore.example;

import com.alpacacore.AlpacaCore;
import com.alpacacore.ModelAssetDesc;
import com.alpacacore.Model;
import com.alpacacore.Instance;

import java.util.Map;
import java.util.Enumeration;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.jar.Manifest;

public class LocalDummyExample {
    public static void main(String[] args) throws Exception {
        System.out.println("Dummy example");

        URL manifestUrl = LocalDummyExample.class.getClassLoader().getResource("ac/example-manifest.mf");
        Manifest manifest = new Manifest(manifestUrl.openStream());
        String pluginDir = manifest.getMainAttributes().getValue("aclp_out_dir");
        String llamaDataDir = manifest.getMainAttributes().getValue("ac_test_data_llama_dir");

        AlpacaCore.addPluginDir(pluginDir);
        AlpacaCore.loadAllPlugins();

        ModelAssetDesc desc = new ModelAssetDesc();
        desc.type = "llama.cpp gguf";
        desc.assets = new ModelAssetDesc.AssetInfo[] {
            new ModelAssetDesc.AssetInfo(llamaDataDir + "/gpt2-117m-q6_k.gguf", "")
        };
        desc.name = "gpt2 117m";

        Model model = AlpacaCore.loadModel(desc, null, null);

        Instance instance = model.createInstance("general", null);

        Map result = (Map)instance.runOp("run", Map.of(
            "prompt", "Eating an apple every day will",
            "max_tokens", 30
        ), null);

        System.out.println(result);

        AlpacaCore.releaseInstance(instance);
        AlpacaCore.releaseModel(model);
    }
}
