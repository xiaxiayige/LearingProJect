import tinify
import sys
import os
from os import path

tinify.key = " your key"

rootPath=path.abspath(os.getcwd());

#print(rootPath)

def getFileList():
    
    return os.listdir(rootPath)

path_array=getFileList()

#print(path_array)

print("开始转换")

length=len(path_array)

for position in range(0,length):
    
    filename=path_array[position]
    
    if filename.find(".py") == -1:
        try:
            fullname=str(rootPath+'\\'+filename)
            #print(fullname)
            source = tinify.from_file(fullname)
            source.to_file(fullname)
            print("转换完成{0}/{1}".format(position+1,length))
        except tinify.Error:
            print("转换异常")
            pass
    else:
        pass
    
print("转换结束")

