package plugins;

import android.util.Log;
import android.util.Base64;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import pe.com.entel.integracion.RSA_Encryption;

public class EntelPay extends CordovaPlugin {

    @Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.e("Ingreso",action);

        if(action.equals("encriptar")) {
            
            try{
                    Log.e("Ingreso","encriptar");
                    String dato = args.getString(0);

                    RSA_Encryption rsa = new RSA_Encryption();
			
                    String key = rsa.getKeyMobile();
                    byte[] cipherTextKey = Base64.decode(key, Base64.DEFAULT);

                    String initVector = rsa.getVectorMobile();
                    byte[] cipherTextVector = Base64.decode(initVector, Base64.DEFAULT);;

                    byte[] enc = rsa.encrypted_(dato,cipherTextKey,cipherTextVector);
                    String encryptRSA = Base64.encodeToString(enc,Base64.DEFAULT);

                    JSONObject result = new JSONObject();
                    result.put("result", encryptRSA);

                    Log.e("result",result.toString());

                    callbackContext.success(result.toString());

                    return true;

            }
            catch(Exception e){
                
                JSONObject result = new JSONObject();
                result.put("Error", e.getMessage());

                callbackContext.error(result);
                return false;
            }
            
            
        }
        if(action.equals("desencriptar")) {

            try{

            Log.e("Ingreso","desencriptar");

            RSA_Encryption rsa = new RSA_Encryption();

            String dato = args.getString(0);
			
			String key = rsa.getKeyMobile();
			byte[] cipherTextKey = Base64.decode(key, Base64.DEFAULT);
			
			String initVector = rsa.getVectorMobile();
            byte[] cipherTextVector = Base64.decode(initVector, Base64.DEFAULT);
			
			
			byte[] findsc = Base64.decode(dato,Base64.DEFAULT);
            String decryptRSA = rsa.decrypted_(findsc,cipherTextKey,cipherTextVector);

            JSONObject result = new JSONObject();
            result.put("result", decryptRSA);

            Log.e("result",result.toString());

            callbackContext.success(result.toString());

            return true;

            }catch(Exception e){
                
                JSONObject result = new JSONObject();
                result.put("Error", e.getMessage());

                callbackContext.error(result);
                return false;
            }

        }
        else
            return false;
    }
}