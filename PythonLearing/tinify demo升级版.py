import os
import tinify
from os import path

tinify.key = "your key"
rootPath=os.getcwd()

print(rootPath)

fileArray=[]

def getAllImg(fileList,rootPath):
    files=os.listdir(rootPath)
    for fileName in files:
        fullpath=path.join(rootPath,fileName)
        if(path.isdir(fullpath)):
            getAllImg(fileList,fullpath)
        else:
            if '.png' in fullpath or '.jpg' in fullpath  or '.jpeg' in fullpath:
                fileList.append(fullpath)
            else:
                pass
    return fileList

def convert(path_array):
    print("开始转换")

    length=len(path_array)

    for position in range(0,length):
       
        filename=path_array[position]
        try:
            #print(fullname)
            source = tinify.from_file(filename)
            source.to_file(filename)
            print("转换完成{0}/{1}".format(position+1,length))
        except tinify.Error:
            print("转换异常")
            pass
      
        
    print("转换结束")
            
getAllImg(fileArray,rootPath)

convert(fileArray)
