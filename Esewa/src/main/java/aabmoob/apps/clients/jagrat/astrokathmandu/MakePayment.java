package aabmoob.apps.clients.jagrat.astrokathmandu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eSewa.fonepay.ESewaConfiguration;
import com.eSewa.fonepay.ESewaPayment;
import com.eSewa.fonepay.ESewaPaymentActivity;


public class MakePayment extends AppCompatActivity {

    //esewa merchent info
    public String merchentId = "UkBRTQASCQgWChFLFhEDGEsaCRoAGRUARQ8YAgEEA08SGBELChgEAwkeCgsdEA==";
    private String marchentSecret = "BBIHGg4cCUsYFQMWWQIfAgAXEQBLHQAUGQQNSxIWAxMcAAQNDR4EGQUG";

    ESewaConfiguration eSewaConfiguration;
    private int REQUEST_CODE_PAYMENT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_payment);

        eSewaConfiguration = new ESewaConfiguration()
                .clientId(merchentId)
                .secretKey(marchentSecret)
                .environment(ESewaConfiguration.ENVIRONMENT_TEST);

        findViewById(R.id.btn_make_a_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ESewaPayment eSewaPayment = new ESewaPayment("50", "Astrology question", "3343434", "http://astrokathmandu.host22.com/");
                Intent intent = new Intent(MakePayment.this, ESewaPaymentActivity.class);
                intent.putExtra(ESewaConfiguration.ESEWA_CONFIGURATION,
                        eSewaConfiguration);
                intent.putExtra(ESewaPayment.ESEWA_PAYMENT, eSewaPayment);
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            }


        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Toast.makeText(this, "Message id: " + s, Toast.LENGTH_SHORT).show();
                Log.d("response: ", s);

            } else if (resultCode == RESULT_CANCELED) {
             // Do Something
            } else if (resultCode == ESewaPayment.RESULT_EXTRAS_INVALID) {
                String s = data.getStringExtra(ESewaPayment.EXTRA_RESULT_MESSAGE);
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

            }
        }
    }
}
