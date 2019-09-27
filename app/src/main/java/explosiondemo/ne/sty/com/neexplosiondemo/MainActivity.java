package explosiondemo.ne.sty.com.neexplosiondemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import explosiondemo.ne.sty.com.neexplosiondemo.explosion.ClickCallback;
import explosiondemo.ne.sty.com.neexplosiondemo.explosion.ExplosionField;
import explosiondemo.ne.sty.com.neexplosiondemo.explosion.FallingParticleFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ExplosionField explosionField = new ExplosionField(this, new FallingParticleFactory());
        explosionField.setClickCallback(new ClickCallback() {
            @Override
            public void onClick(View v) {
                Log.i("sty", "onClick: " +  v.toString() );
            }
        });
        explosionField.addListener(findViewById(R.id.text));
        explosionField.addListener(findViewById(R.id.image));
        explosionField.addListener(findViewById(R.id.layout));
    }


}
