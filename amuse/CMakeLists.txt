cmake_minimum_required(VERSION 3.4.1)
add_library(multidex SHARED src/main/jni/multidex.cpp)
target_link_libraries(multidex log z)