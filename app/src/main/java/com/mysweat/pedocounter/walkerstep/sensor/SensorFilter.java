package com.mysweat.pedocounter.walkerstep.sensor;


public class SensorFilter {
    private SensorFilter() {
    }

    public static float sum(float[] fArr) {
        float f = 0.0f;
        for (float f2 : fArr) {
            f += f2;
        }
        return f;
    }

    public static float[] cross(float[] fArr, float[] fArr2) {
        return new float[]{(fArr[1] * fArr2[2]) - (fArr[2] * fArr2[1]), (fArr[2] * fArr2[0]) - (fArr[0] * fArr2[2]), (fArr[0] * fArr2[1]) - (fArr[1] * fArr2[0])};
    }

    public static float norm(float[] fArr) {
        float f = 0.0f;
        for (int i = 0; i < fArr.length; i++) {
            f += fArr[i] * fArr[i];
        }
        return (float) Math.sqrt(f);
    }

    public static float dot(float[] fArr, float[] fArr2) {
        return (fArr[0] * fArr2[0]) + (fArr[1] * fArr2[1]) + (fArr[2] * fArr2[2]);
    }

    public static float[] normalize(float[] fArr) {
        float[] fArr2 = new float[fArr.length];
        float norm = norm(fArr);
        for (int i = 0; i < fArr.length; i++) {
            fArr2[i] = fArr[i] / norm;
        }
        return fArr2;
    }
}
