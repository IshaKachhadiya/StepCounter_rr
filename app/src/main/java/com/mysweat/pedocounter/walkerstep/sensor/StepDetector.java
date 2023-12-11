package com.mysweat.pedocounter.walkerstep.sensor;

import android.content.Context;


public class StepDetector {
    private static float STEP_THRESHOLD = 15.0f;
    private StepListener listener;
    Context mContext;
    private int accelRingCounter = 0;
    private float[] accelRingX = new float[50];
    private float[] accelRingY = new float[50];
    private float[] accelRingZ = new float[50];
    private int velRingCounter = 0;
    private float[] velRing = new float[10];
    private long lastStepTimeNs = 0;
    private float oldVelocityEstimate = 0.0f;

    public void registerListener(StepListener stepListener, Context context) {
        this.listener = stepListener;
        this.mContext = context;
        STEP_THRESHOLD = Float.parseFloat(context.getSharedPreferences("sensitivity", 0).getString("val", "0"));
    }

    public void updateAccel(long j, float f, float f2, float f3) {
        float[] fArr = {f, f2, f3};
        int i = this.accelRingCounter + 1;
        this.accelRingCounter = i;
        float[] fArr2 = this.accelRingX;
        fArr2[i % 50] = fArr[0];
        this.accelRingY[i % 50] = fArr[1];
        this.accelRingZ[i % 50] = fArr[2];
        float[] fArr3 = {SensorFilter.sum(fArr2) / Math.min(this.accelRingCounter, 50), SensorFilter.sum(this.accelRingY) / Math.min(this.accelRingCounter, 50), SensorFilter.sum(this.accelRingZ) / Math.min(this.accelRingCounter, 50)};
        float norm = SensorFilter.norm(fArr3);
        fArr3[0] = fArr3[0] / norm;
        fArr3[1] = fArr3[1] / norm;
        fArr3[2] = fArr3[2] / norm;
        int i2 = this.velRingCounter + 1;
        this.velRingCounter = i2;
        float[] fArr4 = this.velRing;
        fArr4[i2 % 10] = SensorFilter.dot(fArr3, fArr) - norm;
        float sum = SensorFilter.sum(fArr4);
        if (STEP_THRESHOLD == 0.0f) {
            STEP_THRESHOLD = 15.0f;
        }
        float f4 = STEP_THRESHOLD;
        if (sum > f4 && this.oldVelocityEstimate <= f4 && j - this.lastStepTimeNs > 250000000) {
            this.listener.step(j);
            this.lastStepTimeNs = j;
        }
        this.oldVelocityEstimate = sum;
    }

    public static void updateSensVal(float f) {
        STEP_THRESHOLD = f;
    }
}
