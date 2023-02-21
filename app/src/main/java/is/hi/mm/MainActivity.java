package is.hi.mm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import is.hi.mm.entities.Recipe;
import is.hi.mm.networking.NetworkCallback;
import is.hi.mm.networking.NetworkManager;

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

    //private List<Recipe> mRecipeList;

    private RecyclerView mRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viljum sækja af bakendanum
        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.getRecipes(new NetworkCallback<List<Recipe>> () {
            @Override
            public void onSuccess(List<Recipe> result) {
                //hvað viljum við gera þegar við fáum listann af Recipes.
                //viljum setja það í eitthvað eins og RecyclerView
                mRecipeList = (RecyclerView) result;
                //mQuestionBank = result;
            }

            @Override
            public void onFailure(String errorString) {
                Log.e(TAG, errorString);
            }
        });

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