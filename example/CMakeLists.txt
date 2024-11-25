# Copyright (c) Alpaca Core
# SPDX-License-Identifier: MIT
#
CPMAddPackage(
    NAME ac-test-data-llama
    VERSION 1.0.0
    GIT_REPOSITORY https://huggingface.co/alpaca-core/ac-test-data-llama
    GIT_TAG e6860e89d869ea815a4107bce3bb41af9ad1c1f0
)
make_ac_local_plugin_available(
    NAME ilib-llama.cpp
    VERSION 1.0.0
    GITHUB alpaca-core/ilib-llama.cpp
)

configure_file(
    example-manifest.mf.in
    example-manifest.mf
    @ONLY
)

add_jar(example-local-dummy-jar
    SOURCES
        LocalDummyExample.java
    RESOURCES NAMESPACE ac
        ${CMAKE_CURRENT_BINARY_DIR}/example-manifest.mf
    INCLUDE_JARS
        alpaca-core-jar
    OUTPUT_NAME
        ac-example-local-dummy
)
