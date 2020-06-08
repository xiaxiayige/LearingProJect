## 该文件放置在Flutter项目根目录（与同pubspec.yaml 同级目录）
## 运行即可将文件assets下面的资源文件添加到 assets 标签下

## version 1.0
import os
from os import path


# 获取配置文件信息

def getYamlConfig():
    with open("pubspec.yaml") as f:
        line = [line.rstrip() for line in f]
    return line


config = getYamlConfig()
newConfig = config

print(config)


def getAssets():
    rootPath = os.getcwd()
    dirPath = rootPath + "\\assets"
    files = os.listdir(dirPath)
    return files


for index in range(len(config)):
    print("index = ", index)

for index in range(len(config)):
    if (config[index] == "  assets:"):
        assets = getAssets()
        for k in range(len(assets)):
            assets_k_ = "      - " + assets[k]
            newConfig.insert(index + 1, assets_k_)
            print(newConfig[index + k + 1])

with open("pubspec2.yaml", mode="w") as f:
    for line in newConfig:
        f.write(line)
        f.write("\n")
