package hexui_lib.util;

import com.badlogic.gdx.Gdx;

public class Wobbler {
    public float[] x;
    public float[] x_min_soft;
    public float[] x_max_soft;
    public float[] x_min_hard;
    public float[] x_max_hard;
    public int dimensions;
    public Wobbler velocity = null;
    public float time = 0f;
    public float wavelength = 1f;

    public Wobbler(float wavelength){
        this(wavelength, 1, 1);
    }

    public Wobbler(float wavelength, int dimensions){
        this(wavelength, dimensions, 1);
    }

    public Wobbler(float wavelength, int dimensions, int levels){
        this.wavelength = wavelength;
        time = (float) (Math.random()*wavelength);
        if(levels > 1){
            velocity = new Wobbler(wavelength, dimensions, levels-1);
        }
        this.dimensions = dimensions;
        x           = new float[dimensions];
        x_min_soft  = new float[dimensions];
        x_max_soft  = new float[dimensions];
        x_min_hard  = new float[dimensions];
        x_max_hard  = new float[dimensions];

        for(int i=0; i<dimensions; i++){
            x[i] = 0f;
            x_min_soft[i] = -0.8f;
            x_max_soft[i] =  0.8f;
            x_min_hard[i] = -1.0f;
            x_max_hard[i] =  1.0f;
        }
    }

    public void step(){
        if(velocity != null){
            velocity.step();
            for(int i=0; i<dimensions; i++){
                x[i] += velocity.get(i)*0.1f;
                if(x[i] > x_max_soft[i] || x[i] < x_min_soft[i]){
                    x[i] *= 0.9f;
                }
                if(x[i] > x_max_hard[i]){
                    x[i] = x_max_hard[i];
                }else if(x[i] < x_min_hard[i]){
                    x[i] = x_min_hard[i];
                }
            }
        }else{
            for(int i=0; i<dimensions; i++){
                time += Gdx.graphics.getDeltaTime();
                if(i%2 == 0) {
                    x[i] = (float) Math.cos(time/wavelength);
                }else{
                    x[i] = (float) Math.sin(time/wavelength);
                }
            }
        }
    }

    public float get(int i){
        return x[i];
    }
}
