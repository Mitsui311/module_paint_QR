# Kobe University
# Advanced Course on Image Processing
# 2021.9.13

import cv2
import numpy as np
import sys

#------------------------------------
def func(src1, src2):
    #画像のサイズ取得
    if src1.shape != src2.shape:
        print("Different size")
        sys.exit(0)
    
    rows = src1.shape[0]
    cols = src1.shape[1]
    
    #出力用画像生成
    dst = np.zeros((rows, cols, 3), 'float32')
    
    #1画素ずつ計算する例
    for y in range(0, rows):
        for x in range(0, cols):
            for c in range(0, 3):
                dst[y,x,c] = (src1[y,x,c] + src2[y,x,c]) / 2

    return dst

#------------------------------------
#ファイル読み込み（浮動小数点型に変換）
img1 = cv2.imread("content1_1.png").astype('float32')
img2 = cv2.imread("content2.png").astype('float32')

img3 = func(img1, img2)         #画像処理

cv2.imwrite("output.jpg", img3.astype('uint8'))     #ファイルに保存(8ビットに戻す)
    
