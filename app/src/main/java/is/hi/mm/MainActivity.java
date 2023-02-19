package is.hi.mm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    /*

    Dæni/Note to self:
    private Button mButtonTrue
    private Button mButtonFalse
    muna eftir ad gefa hlutum ID
    allur texti er skilgreindur i strings.xml
    etc

    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        TODO: Laga RestControllers á hinu verkefninu
        TODO: Setja upp entities, activities, services, network manager
        TODO: Læra betur um fragments
        TODO: Setja upp strengi
        TODO: Skoða útlit fyrir app
        TODO: Sækja libraries til að meðhöndla myndir ofl
        TODO: Horfa a restina af support videos

        mButtonTrue = (Button) findViewById(R.id.true_button td
        mButtonTrue.setOnClickListener(new View.OnClickListener() >> android studio klarar
        at override
        public void onClick(View v) {
                    logic
                    Toast.makeText(MainActivity.this, R.string.toast_correct, Toast.LENGTH_SHORT).show()
                    etc
        }

         */

    }

}