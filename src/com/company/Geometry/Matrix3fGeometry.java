package com.company.Geometry;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Matrix3f;

/**
 * Облегчаем использование Matrix3f для наших целей
 * А именно: создание матриц поворота, масштаба, переноса
 */
public class Matrix3fGeometry {
    public static void createTranslateMatrix(Vector2f translateVector, Matrix3f dest)
    {
        //Матрица переноса
        //[1  0  0]
        //[0  1  0]
        //[Tx Ty 1]
        Matrix3f.setZero(dest);
        dest.m00 = 1.0f;
        dest.m11 = 1.0f;
        dest.m20 = translateVector.getX(); dest.m21 = translateVector.getY(); dest.m22 = 1.0f;
    }

    public static void createRotationMatrix(float angle, Matrix3f dest)
    {
        //Матрица поворота на угол A
        //[cosA -sinA 0]
        //[sinA cosA  0]
        //[  0    0   1]
        float cos = (float)Math.cos(angle); float sin = (float)Math.sin(angle);
        Matrix3f.setZero(dest);
        dest.m00 = cos; dest.m01 = -sin;
        dest.m10 = sin; dest.m11 = cos;
        dest.m22 = 1.0f;
    }

    public static void createScaleMatrix(Vector2f scale, Matrix3f dest)
    {
        //Матрица масштаба
        //[Sx 0  0]
        //[0 Sy  0]
        //[0  0  1]
        Matrix3f.setZero(dest);
        dest.m00 = scale.getX();
        dest.m11 = scale.getY();
        dest.m22 = 1.0f;
    }
}
