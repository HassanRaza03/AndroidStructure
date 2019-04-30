//package edu.aku.family_hifazat.helperclasses;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.util.Log;
//
//import com.paypal.android.sdk.payments.PayPalConfiguration;
//import com.paypal.android.sdk.payments.PayPalPayment;
//import com.paypal.android.sdk.payments.PayPalService;
//import com.paypal.android.sdk.payments.PaymentActivity;
//import com.paypal.android.sdk.payments.PaymentConfirmation;
//
//import edu.aku.family_hifazat.helperclasses.ui.helper.UIHelper;
//import edu.aku.family_hifazat.activities.BaseActivity;
//
//import java.math.BigDecimal;
//
//import static android.content.ContentValues.TAG;
//
//
///**
// * Created by khanhamza on 24-Apr-17.
// */
//
//public class PaypalHelper {
//
//    private static final String CONFIG_CLIENT_ID_SANDBOX = "ATebDVIEGrovfLd3mes-Lsc1o2hO1jXoMZBjoAna3psg8ZiBpcW2AF0YAi4tjkr8SAlQpfJFw78vYv5s";//SSANDBOX
//    public static final int REQUEST_CODE_PAYPAL = 1122;
//
//
//    private PayPalConfiguration payPalConfiguration;
//    private String payPalClientID = CONFIG_CLIENT_ID_SANDBOX;
//    private Intent service;
//    private BaseActivity context;
//    private String price;
//    private PayPalInterface payPalinterface;
//
//
//    public static PaypalHelper paypalHelper;
//
//
//    // Singleton class
//
//    private PaypalHelper() {
//
//    }
//
//    public static PaypalHelper getInstance() {
//        if (paypalHelper == null) {
//            paypalHelper = new PaypalHelper();
//        }
//        return paypalHelper;
//    }
//
//    public void configurePayPal(BaseActivity context, String price, PayPalInterface paypalInterface) {
//        this.context = context;
//        this.price = price;
//        this.payPalinterface = paypalInterface;
//
//
//        payPalConfiguration = new PayPalConfiguration()
//                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // sandbox for test, production for real
//                .clientId(payPalClientID)
//                .merchantName("Smart Mart")
//                .merchantPrivacyPolicyUri(Uri.parse("https://www.youtube.com/watch?v=mTcM8sYZHas"))
//                .merchantUserAgreementUri(Uri.parse("https://www.youtube.com/watch?v=mTcM8sYZHas"));
//
//
//        service = new Intent(context, PayPalService.class);
//        service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration); // configuration above
//        context.startService(service);
//    }
//
//
//    // Calling from onClick Payment button
//
//    public void pay() {
//        PayPalPayment payment = new PayPalPayment(new BigDecimal(price), "USD", "Payment with Paypal",
//                PayPalPayment.PAYMENT_INTENT_SALE);
//
//        Intent intent = new Intent(context, PaymentActivity.class); // it's paymentactivity !
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//        context.startActivityForResult(intent, REQUEST_CODE_PAYPAL);
//    }
//
//    // Calling from Main activity onActivityResult
//
//    public void onResult(int requestCode, int resultCode, Intent data) {
//
//        if (resultCode == Activity.RESULT_OK) {
//            // we have to confirm that the payment worked to avoid fraud
//            PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//            if (confirmation != null) {
//                String state = confirmation.getProofOfPayment().getState();
//                payPalinterface.payPalInterface(confirmation, state);
//                Log.d(TAG, "onResult: " + state);
////  Implemented these checks in fragment
//
////                    if (state.equals("approved")) // if the payment worked, the state equals approved
////                    {
////                        UIHelper.showToast(context, "payment approved");
////                    } else {
////                        UIHelper.showToast(context, "error in the payment");
////                    }
//            } else {
//                UIHelper.showToast(context, "confirmation is null");
//            }
//        }
//    }
//
//    public interface PayPalInterface {
//        void payPalInterface(PaymentConfirmation paymentConfirmation, String state);
//    }
//}
