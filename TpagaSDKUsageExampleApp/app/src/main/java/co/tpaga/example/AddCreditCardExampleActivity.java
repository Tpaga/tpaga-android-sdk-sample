package co.tpaga.example;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import co.tpaga.android.FragmentCreditCard.AddCreditCardFragment;
import co.tpaga.android.FragmentCreditCard.AddCreditCardView;
import co.tpaga.android.Tools.TpagaException;

public class AddCreditCardExampleActivity extends AppCompatActivity implements AddCreditCardView.UserActionsListener {

    private AddCreditCardFragment mAddCreditCardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card_with_fragment);
        addCreditCardFragment();
    }

    private void addCreditCardFragment() {
        try {
            mAddCreditCardFragment = new AddCreditCardFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_default, mAddCreditCardFragment, AddCreditCardFragment.TAG);
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponseSuccessTokenizeCreditCard(String creditCardToken) {
        mAddCreditCardFragment.clearFields();
        Toast.makeText(this, "credit card " + creditCardToken, Toast.LENGTH_LONG).show();
        /**
         * you must send the card token to your server to use in payments
         */
    }

    @Override
    public void showError(Throwable t) {
        if (t instanceof TpagaException)
            ((TpagaException) t).getStatusCode();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
        t.printStackTrace();
    }
}
