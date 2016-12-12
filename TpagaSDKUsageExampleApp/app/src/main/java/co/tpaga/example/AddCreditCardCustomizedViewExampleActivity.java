package co.tpaga.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.tpaga.android.Entities.CreditCard;
import co.tpaga.android.FragmentCreditCard.AddCreditCardView;
import co.tpaga.android.Tools.TpagaException;
import co.tpaga.android.Tools.TpagaTools;
import co.tpaga.android.Tpaga;

public class AddCreditCardCustomizedViewExampleActivity extends AppCompatActivity implements AddCreditCardView.UserActionsListener {

    @BindView(R.id.et_cc_number_til)
    TextInputLayout ccNumberTil;
    @BindView(R.id.et_cc_number)
    EditText cc_number;

    @BindView(R.id.et_month)
    MaterialBetterSpinner month;

    @BindView(R.id.et_year)
    MaterialBetterSpinner year;

    @BindView(R.id.et_cvv_number_til)
    TextInputLayout cvvTil;
    @BindView(R.id.et_cvv_number)
    EditText cvv;

    @BindView(R.id.et_name_in_card_til)
    TextInputLayout nameTil;
    @BindView(R.id.et_name_in_card)
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        ButterKnife.bind(this);
        setSpinnerYears();
        setSpinnerMonths();

        name.setText("");
        cvv.setText("");
        cc_number.setText("");
        year.setSelection(0);
        month.setSelection(0);
    }

    private void setSpinnerMonths() {
        ArrayList<String> months = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            months.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterMonths = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, months);
        month.setAdapter(adapterMonths);
        adapterMonths.notifyDataSetChanged();

    }

    private void setSpinnerYears() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i <= thisYear + 20; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterYears = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, years);
        year.setAdapter(adapterYears);
        adapterYears.notifyDataSetChanged();
    }

    @OnClick(R.id.bt_scan_card)
    public void onScanPress() {
        Tpaga.startScanCreditCard(this);
    }

    @OnClick(R.id.bt_add_cc_request)
    public void onClickAddCC() {
        if (!Tpaga.validateCreditCardData(getCreditCard())) {
            TpagaTools.showToast(this, getString(R.string.verify_field));
            return;
        }
        Tpaga.tokenizeCreditCard(this, getCreditCard());
    }

    public CreditCard getCreditCard() {
        return CreditCard.create(
                cc_number.getText().toString().replaceAll("\\s+", ""),
                year.getText().toString(),
                month.getText().toString(),
                cvv.getText().toString(),
                name.getText().toString());
    }

    @Override
    public void onResponseSuccessTokenizeCreditCard(String creditCardToken) {
        Toast.makeText(this, "credit card " + creditCardToken, Toast.LENGTH_LONG).show();
        /**
         * you must send the card token to your server to use in payments
         */
    }

    @Override
    public void showError(Throwable t) {
        if (t instanceof TpagaException)
            ((TpagaException) t).getStatusCode();
        TpagaTools.showToast(this, t.getMessage());
        t.printStackTrace();
    }

    public void onResultScanCreditCard(CreditCard creditCard) {
        cc_number.setText(creditCard.primaryAccountNumber);
        if (creditCard.expirationYear != null && !creditCard.expirationYear.isEmpty()) {
            year.setText(creditCard.expirationYear);
        }
        if (creditCard.expirationMonth != null && !creditCard.expirationMonth.isEmpty()) {
            month.setText(creditCard.expirationMonth);
        }
        if (creditCard.cvc != null && !creditCard.cvc.isEmpty()) {
            cvv.setText(creditCard.cvc);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Tpaga.SCAN_CREDIT_CARD:
                if (resultCode == 13274388) {
                    onResultScanCreditCard(Tpaga.onActivityResultScanCreditCard(data));
                }
                break;
        }
    }
}
