package explosiondemo.ne.sty.com.neexplosiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import explosiondemo.ne.sty.com.neexplosiondemo.explosion.ClickCallback;
import explosiondemo.ne.sty.com.neexplosiondemo.explosion.ExplosionField;
import explosiondemo.ne.sty.com.neexplosiondemo.explosion.falling.FallingParticleFactory;
import explosiondemo.ne.sty.com.neexplosiondemo.explosion.upfalling.ExplodeParticleFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        // 先上后下的爆炸动画
        ExplosionField explosionField = new ExplosionField(this, new ExplodeParticleFactory());
        explosionField.setClickCallback(new ClickCallback() {
            @Override
            public void onClick(View v) {
                Log.i("sty", "onClick1: " +  v.toString() );
            }
        });

        explosionField.addListener(findViewById(R.id.image));
        explosionField.addListener(findViewById(R.id.iv_windmill));

        // 仅仅下落的爆炸动画
        explosionField = new ExplosionField(this, new FallingParticleFactory());
        explosionField.setClickCallback(new ClickCallback() {
            @Override
            public void onClick(View v) {
                Log.i("sty", "onClick2: " +  v.toString() );
            }
        });
        explosionField.addListener(findViewById(R.id.layout));
        explosionField.addListener(findViewById(R.id.text));
    }


}
