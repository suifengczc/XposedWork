cmake_minimum_required(VERSION 3.4.1)

# 指定源文件目录
#AUX_SOURCE_DIRECTORY(${PROJECT_SOURCE_DIR}/app/src/main/jni SRC_LIST)
message(STATUS "******************************************************************")
message(STATUS "CMAKE_SOURCE_DIR->" ${CMAKE_SOURCE_DIR})
message(STATUS "PROJECT_SOURCE_DIR->)" ${PROJECT_SOURCE_DIR})
message(STATUS "******************************************************************")

add_library(nativesupport SHARED StringUtils.cpp)
find_library(log-lib log)

target_link_libraries(
        nativesupport
        ${log-lib}
)